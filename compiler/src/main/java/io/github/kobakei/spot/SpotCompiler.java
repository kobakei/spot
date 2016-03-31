package io.github.kobakei.spot;

import com.google.auto.service.AutoService;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

@AutoService(Processor.class)
@SupportedAnnotationTypes({
        "io.github.kobakei.spot.annotation.Table",
        "io.github.kobakei.spot.annotation.PrefInt",
        "io.github.kobakei.spot.annotation.PrefString"
})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class SpotCompiler extends AbstractProcessor {

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
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return false;
    }

}
