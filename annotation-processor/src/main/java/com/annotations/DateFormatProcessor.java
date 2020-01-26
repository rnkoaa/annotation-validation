package com.annotations;

import annotations.DateFormat;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@SupportedAnnotationTypes(
        "annotations.DateFormat")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class DateFormatProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Messager messager = processingEnv.getMessager();
        for (TypeElement annotation : annotations) {
            Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(annotation);
            for (Element element : elementsAnnotatedWith) {
                DateFormat dateFormatAnnotation = element.getAnnotation(DateFormat.class);
                TypeMirror typeMirror = element.asType();

                //checking the filed type
                //we don't have field class type information as code is not yet built
                //so we have to compare the strings
                if (!typeMirror.toString().equals(LocalDate.class.getName())) {
                    messager.printMessage(Diagnostic.Kind.ERROR,
                            "The type annotated with @DateFormat must be LocalDate");
                }

                try {
                    //validating date format
                    DateTimeFormatter simpleDateFormat =
                            DateTimeFormatter.ofPattern(dateFormatAnnotation.value());
                    LocalDate.now().format(simpleDateFormat);

                } catch (Exception e) {
                    messager.printMessage(Diagnostic.Kind.ERROR,
                            "Not a valid date format " + dateFormatAnnotation.value());
                }

            }
        }
        return false;
    }

//    @Override
//    public void init(ProcessingEnvironment processingEnvironment) {
//        super.init(processingEnvironment);
//
//        // get messager for printing errors
//        messager = processingEnvironment.getMessager();
//    }
//    @Override
//    public SourceVersion getSupportedSourceVersion() {
//        return SourceVersion.latestSupported();
//    }
//
//    @Override
//    public Set<String> getSupportedAnnotationTypes() {
//        return new HashSet<>(Collections.singletonList(DateFormat.class.getName()));
//    }
}
