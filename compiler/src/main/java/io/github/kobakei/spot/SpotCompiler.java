package io.github.kobakei.spot;

import android.content.Context;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

import io.github.kobakei.spot.annotation.Pref;
import io.github.kobakei.spot.annotation.PrefField;
import io.github.kobakei.spot.internal.PreferencesUtil;

@AutoService(Processor.class)
public class SpotCompiler extends AbstractProcessor {

    private static final boolean LOGGABLE = false;

    private Filer filer;
    private Messager messager;
    private Elements elements;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.messager = processingEnv.getMessager();
        this.filer = processingEnv.getFiler();
        this.elements = processingEnv.getElementUtils();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(Pref.class.getCanonicalName());
        return set;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        log("*** process START ***");
        Class<Pref> tableClass = Pref.class;
        for (Element element : roundEnv.getElementsAnnotatedWith(tableClass)) {
            ElementKind kind = element.getKind();
            if (kind == ElementKind.CLASS) {
                try {
                    log("*** Found table. Generating repository ***");
                    generateRepositoryClass(element);
                } catch (IOException e) {
                    logError("IO error");
                }
            } else {
                logError("Type error");
            }
        }

        log("*** process END ***");

        return true;
    }

    /**
     * Generate YourModel$$Repository class
     * @param element
     * @throws IOException
     */
    private void generateRepositoryClass(Element element) throws IOException {
        String packageName = elements.getPackageOf(element).getQualifiedName().toString();
        ClassName entityClass = ClassName.get(packageName, element.getSimpleName().toString());
        ClassName stringClass = ClassName.get(String.class);
        ClassName contextClass = ClassName.get(Context.class);

        Pref prefAnnotation = element.getAnnotation(Pref.class);
        String tableName = prefAnnotation.name();

        MethodSpec constructorSpec = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .build();

        MethodSpec getNameSpec = MethodSpec.methodBuilder("getName")
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL, Modifier.STATIC)
                .returns(stringClass)
                .addStatement("return $S", tableName)
                .build();

        MethodSpec.Builder getEntitySpecBuilder = MethodSpec.methodBuilder("getEntity")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
                .addParameter(contextClass, "context")
                .returns(entityClass)
                .addStatement("$T entity = new $T()", entityClass, entityClass);

        MethodSpec.Builder putEntitySpecBuilder = MethodSpec.methodBuilder("putEntity")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
                .addParameter(contextClass, "context")
                .addParameter(entityClass, "entity");

        for (Element element1 : element.getEnclosedElements()) {
            if (element1.getAnnotation(PrefField.class) != null) {
                handlePrefField(element1, getEntitySpecBuilder, putEntitySpecBuilder);
            }
        }

        getEntitySpecBuilder.addStatement("return entity");

        String className = element.getSimpleName() + "SpotRepository";
        TypeSpec repository = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(constructorSpec)
                .addMethod(getNameSpec)
                .addMethod(getEntitySpecBuilder.build())
                .addMethod(putEntitySpecBuilder.build())
                .build();

        JavaFile.builder(packageName, repository)
                .build()
                .writeTo(filer);
    }

    private void handlePrefField(Element element1, MethodSpec.Builder getEntitySpecBuilder,
                                 MethodSpec.Builder putEntitySpecBuilder) {
        PrefField pref = element1.getAnnotation(PrefField.class);

        // Convert class
        TypeName convertClass = getConvertClass(pref);
        String className = convertClass.toString();
        if ("java.lang.Void".equals(className)) {
            className = element1.asType().toString();
        }

        if ("int".equals(className) || "java.lang.Integer".equals(className)) {
            handlePrefInt(element1, getEntitySpecBuilder, putEntitySpecBuilder);
        } else if ("long".equals(className) || "java.lang.Long".equals(className)) {
            handlePrefLong(element1, getEntitySpecBuilder, putEntitySpecBuilder);
        } else if ("float".equals(className) || "java.lang.Float".equals(className)) {
            handlePrefFloat(element1, getEntitySpecBuilder, putEntitySpecBuilder);
        } else if ("boolean".equals(className) || "java.lang.Boolean".equals(className)) {
            handlePrefBoolean(element1, getEntitySpecBuilder, putEntitySpecBuilder);
        } else if ("java.lang.String".equals(className)) {
            handlePrefString(element1, getEntitySpecBuilder, putEntitySpecBuilder);
        } else if ("java.util.Set<java.lang.String>".equals(className)) {
            handlePrefStringSet(element1, getEntitySpecBuilder, putEntitySpecBuilder);
        }
    }

    private void handlePrefInt(Element element1, MethodSpec.Builder getEntitySpecBuilder,
                               MethodSpec.Builder putEntitySpecBuilder) {
        ClassName utilClass = ClassName.get(PreferencesUtil.class);
        PrefField pref = element1.getAnnotation(PrefField.class);

        TypeName converterClass = getConverterClass(pref);

        getEntitySpecBuilder.beginControlFlow("if ($T.contains(context, getName(), $S))", utilClass, pref.name());

        if (pref.useSetter()) {
            String setterName = getSetterName(element1.getSimpleName().toString());
            getEntitySpecBuilder.addStatement(
                    "entity.$L ( new $T().convertFromSupportedType( $T.getInt(context, getName(), $S, $L) ) )",
                    setterName,
                    converterClass,
                    utilClass,
                    pref.name(),
                    0);
        } else {
            getEntitySpecBuilder.addStatement(
                    "entity.$N = new $T().convertFromSupportedType( $T.getInt(context, getName(), $S, $L) )",
                    element1.getSimpleName(),
                    converterClass,
                    utilClass,
                    pref.name(),
                    0);
        }

        getEntitySpecBuilder.endControlFlow();

        if (pref.useGetter()) {
            String getterName = getGetterName(element1.getSimpleName().toString());
            putEntitySpecBuilder.addStatement(
                    "$T.putInt(context, getName(), $S, new $T().convertToSupportedType(entity.$N()))",
                    utilClass,
                    pref.name(),
                    converterClass,
                    getterName);
        } else {
            putEntitySpecBuilder.addStatement(
                    "$T.putInt(context, getName(), $S, new $T().convertToSupportedType(entity.$N))",
                    utilClass,
                    pref.name(),
                    converterClass,
                    element1.getSimpleName());
        }
    }

    private void handlePrefLong(Element element1, MethodSpec.Builder getEntitySpecBuilder,
                               MethodSpec.Builder putEntitySpecBuilder) {
        ClassName utilClass = ClassName.get(PreferencesUtil.class);
        PrefField pref = element1.getAnnotation(PrefField.class);

        TypeName converterClass = getConverterClass(pref);

        getEntitySpecBuilder.beginControlFlow("if ($T.contains(context, getName(), $S))", utilClass, pref.name());

        if (pref.useSetter()) {
            String setterName = getSetterName(element1.getSimpleName().toString());
            getEntitySpecBuilder.addStatement(
                    "entity.$L ( new $T().convertFromSupportedType( $T.getLong(context, getName(), $S, $L) ) )",
                    setterName,
                    converterClass,
                    utilClass,
                    pref.name(),
                    0L);
        } else {
            getEntitySpecBuilder.addStatement(
                    "entity.$N = new $T().convertFromSupportedType( $T.getLong(context, getName(), $S, $L) )",
                    element1.getSimpleName(),
                    converterClass,
                    utilClass,
                    pref.name(),
                    0L);
        }

        getEntitySpecBuilder.endControlFlow();

        if (pref.useGetter()) {
            String getterName = getGetterName(element1.getSimpleName().toString());
            putEntitySpecBuilder.addStatement(
                    "$T.putLong(context, getName(), $S, new $T().convertToSupportedType(entity.$N()))",
                    utilClass,
                    pref.name(),
                    converterClass,
                    getterName);
        } else {
            putEntitySpecBuilder.addStatement(
                    "$T.putLong(context, getName(), $S, new $T().convertToSupportedType(entity.$N))",
                    utilClass,
                    pref.name(),
                    converterClass,
                    element1.getSimpleName());
        }
    }

    private void handlePrefFloat(Element element1, MethodSpec.Builder getEntitySpecBuilder,
                                  MethodSpec.Builder putEntitySpecBuilder) {
        ClassName utilClass = ClassName.get(PreferencesUtil.class);
        PrefField pref = element1.getAnnotation(PrefField.class);

        TypeName converterClass = getConverterClass(pref);

        getEntitySpecBuilder.beginControlFlow("if ($T.contains(context, getName(), $S))", utilClass, pref.name());

        if (pref.useSetter()) {
            String setterName = getSetterName(element1.getSimpleName().toString());
            getEntitySpecBuilder.addStatement(
                    "entity.$L ( new $T().convertFromSupportedType( $T.getFloat(context, getName(), $S, $Lf) ) )",
                    setterName,
                    converterClass,
                    utilClass,
                    pref.name(),
                    0.0f);
        } else {
            getEntitySpecBuilder.addStatement(
                    "entity.$N = new $T().convertFromSupportedType( $T.getFloat(context, getName(), $S, $Lf) )",
                    element1.getSimpleName(),
                    converterClass,
                    utilClass,
                    pref.name(),
                    0.0f);
        }

        getEntitySpecBuilder.endControlFlow();

        if (pref.useGetter()) {
            String getterName = getGetterName(element1.getSimpleName().toString());
            putEntitySpecBuilder.addStatement(
                    "$T.putFloat(context, getName(), $S, new $T().convertToSupportedType(entity.$N()))",
                    utilClass,
                    pref.name(),
                    converterClass,
                    getterName);
        } else {
            putEntitySpecBuilder.addStatement(
                    "$T.putFloat(context, getName(), $S, new $T().convertToSupportedType(entity.$N))",
                    utilClass,
                    pref.name(),
                    converterClass,
                    element1.getSimpleName());
        }
    }

    private void handlePrefBoolean(Element element1, MethodSpec.Builder getEntitySpecBuilder,
                                   MethodSpec.Builder putEntitySpecBuilder) {
        ClassName utilClass = ClassName.get(PreferencesUtil.class);
        PrefField pref = element1.getAnnotation(PrefField.class);

        TypeName converterClass = getConverterClass(pref);

        getEntitySpecBuilder.beginControlFlow("if ($T.contains(context, getName(), $S))", utilClass, pref.name());

        if (pref.useSetter()) {
            String setterName = getSetterName(element1.getSimpleName().toString());
            getEntitySpecBuilder.addStatement(
                    "entity.$L ( new $T().convertFromSupportedType( $T.getBoolean(context, getName(), $S, $L) ) )",
                    setterName,
                    converterClass,
                    utilClass,
                    pref.name(),
                    false);
        } else {
            getEntitySpecBuilder.addStatement(
                    "entity.$N = new $T().convertFromSupportedType( $T.getBoolean(context, getName(), $S, $L) )",
                    element1.getSimpleName(),
                    converterClass,
                    utilClass,
                    pref.name(),
                    false);
        }

        getEntitySpecBuilder.endControlFlow();

        if (pref.useGetter()) {
            String getterName = getGetterName(element1.getSimpleName().toString());
            putEntitySpecBuilder.addStatement(
                    "$T.putBoolean(context, getName(), $S, new $T().convertToSupportedType(entity.$N()) )",
                    utilClass,
                    pref.name(),
                    converterClass,
                    getterName);
        } else {
            putEntitySpecBuilder.addStatement(
                    "$T.putBoolean(context, getName(), $S, new $T().convertToSupportedType(entity.$N) )",
                    utilClass,
                    pref.name(),
                    converterClass,
                    element1.getSimpleName());
        }
    }

    private void handlePrefString(Element element1, MethodSpec.Builder getEntitySpecBuilder,
                                  MethodSpec.Builder putEntitySpecBuilder) {
        ClassName utilClass = ClassName.get(PreferencesUtil.class);
        PrefField pref = element1.getAnnotation(PrefField.class);

        TypeName converterClass = getConverterClass(pref);

        getEntitySpecBuilder.beginControlFlow("if ($T.contains(context, getName(), $S))", utilClass, pref.name());

        if (pref.useSetter()) {
            String setterName = getSetterName(element1.getSimpleName().toString());
            getEntitySpecBuilder.addStatement(
                    "entity.$L( new $T().convertFromSupportedType( $T.getString(context, getName(), $S, $S) ) )",
                    setterName,
                    converterClass,
                    utilClass,
                    pref.name(),
                    null);
        } else {
            getEntitySpecBuilder.addStatement(
                    "entity.$N = new $T().convertFromSupportedType( $T.getString(context, getName(), $S, $S) )",
                    element1.getSimpleName(),
                    converterClass,
                    utilClass,
                    pref.name(),
                    null);
        }

        getEntitySpecBuilder.endControlFlow();

        if (pref.useGetter()) {
            String getterName = getGetterName(element1.getSimpleName().toString());
            putEntitySpecBuilder.addStatement(
                    "$T.putString(context, getName(), $S, new $T().convertToSupportedType(entity.$N()) )",
                    utilClass,
                    pref.name(),
                    converterClass,
                    getterName);
        } else {
            putEntitySpecBuilder.addStatement(
                    "$T.putString(context, getName(), $S, new $T().convertToSupportedType(entity.$N) )",
                    utilClass,
                    pref.name(),
                    converterClass,
                    element1.getSimpleName());
        }
    }

    private void handlePrefStringSet(Element element1, MethodSpec.Builder getEntitySpecBuilder,
                                     MethodSpec.Builder putEntitySpecBuilder) {
        ClassName utilClass = ClassName.get(PreferencesUtil.class);
        PrefField pref = element1.getAnnotation(PrefField.class);

        TypeName converterClass = getConverterClass(pref);

        getEntitySpecBuilder.beginControlFlow("if ($T.contains(context, getName(), $S))", utilClass, pref.name());

        if (pref.useSetter()) {
            String setterName = getSetterName(element1.getSimpleName().toString());
            getEntitySpecBuilder.addStatement(
                    "entity.$L ( new $T().convertFromSupportedType( $T.getStringSet(context, getName(), $S, null) ) )",
                    setterName,
                    converterClass,
                    utilClass,
                    pref.name());
        } else {
            getEntitySpecBuilder.addStatement(
                    "entity.$N = new $T().convertFromSupportedType( $T.getStringSet(context, getName(), $S, null) )",
                    element1.getSimpleName(),
                    converterClass,
                    utilClass,
                    pref.name());
        }

        getEntitySpecBuilder.endControlFlow();

        if (pref.useGetter()) {
            String getterName = getGetterName(element1.getSimpleName().toString());
            putEntitySpecBuilder.addStatement(
                    "$T.putStringSet(context, getName(), $S, new $T().convertToSupportedType(entity.$N()))",
                    utilClass,
                    pref.name(),
                    converterClass,
                    getterName);
        } else {
            putEntitySpecBuilder.addStatement(
                    "$T.putStringSet(context, getName(), $S, new $T().convertToSupportedType(entity.$N))",
                    utilClass,
                    pref.name(),
                    converterClass,
                    element1.getSimpleName());
        }
    }

    private String getGetterName(String field) {
        String setter = "get";
        if (field.length() > 0) {
            setter += field.substring(0, 1).toUpperCase();
            if (field.length() > 1) {
                setter += field.substring(1);
            }
        }
        return setter;
    }

    private String getSetterName(String field) {
        String setter = "set";
        if (field.length() > 0) {
            setter += field.substring(0, 1).toUpperCase();
            if (field.length() > 1) {
                setter += field.substring(1);
            }
        }
        return setter;
    }

    private TypeName getConverterClass(PrefField prefField) {
        TypeMirror typeMirror = null;
        try {
            prefField.converter();
        } catch (MirroredTypeException e) {
            typeMirror = e.getTypeMirror();
        }
        return ClassName.bestGuess(typeMirror.toString());
    }

    private TypeName getConvertClass(PrefField prefField) {
        TypeElement typeElement = null;
        try {
            prefField.converter();
        } catch (MirroredTypeException e) {
            DeclaredType typeMirror = (DeclaredType) e.getTypeMirror();
            typeElement = (TypeElement) typeMirror.asElement();
        }
        if (typeElement == null) {
            throw new IllegalArgumentException("TypeConverter may be wrong");
        }

        TypeMirror superType = typeElement.getSuperclass();
        TypeMirror arg = ((DeclaredType) superType).getTypeArguments().get(1);
        return ClassName.get(arg);
    }

    private void log(String msg) {
        if (LOGGABLE) {
            this.messager.printMessage(Diagnostic.Kind.OTHER, msg);
        }
    }

    private void logError(String msg) {
        this.messager.printMessage(Diagnostic.Kind.ERROR, msg);
    }
}
