package org.frunix

import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

@SupportedAnnotationTypes("org.frunix.Log")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
class LogProcessor : AbstractProcessor() {
    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
        val allAnnotations: Set<TypeElement> = annotations ?: setOf()

        roundEnv?.let { roundEnvironment ->
            allAnnotations.map { roundEnvironment.getElementsAnnotatedWith(it) }.forEach {
                processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, "found @Log at ${it}")
            }
        }

        return false
    }
}
