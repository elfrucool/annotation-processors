package org.frunix

import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

@SupportedAnnotationTypes("org.frunix.Log")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
class LogProcessor : AbstractProcessor() {
    private val note: (String) -> Unit = issueMsg(Diagnostic.Kind.NOTE)

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
        val allAnnotations: Set<TypeElement> = annotations ?: setOf()

        roundEnv?.let { roundEnvironment ->
            allAnnotations.map(this::printAnnotation)
                    .map { roundEnvironment.getElementsAnnotatedWith(it) }
                    .forEach(this::logAllElements)
        }

        return false
    }

    private fun printAnnotation(typeElement: TypeElement): TypeElement {
        note("annotation: ${typeElement}")
        return typeElement
    }

    private fun logAllElements(elements: Set<Element>?) {
        note("found @Log at ${elements}")
        elements?.forEach {
            note("${it}:")
            note("    kind            : ${it.kind}")
            note("    simpleName      : ${it.simpleName}")
            note("    modifiers       : ${it.modifiers}")
            note("    enclosingElement: ${it.enclosingElement}")
            note("    enclosedElements: ${it.enclosedElements}")
            note("    asType()        : ${it.asType()}")
            note("    asType().kind   : ${it.asType().kind}")
            note("    asType().class  : ${it.asType().javaClass}")
        }
    }

    @Suppress("SameParameterValue")
    private fun issueMsg(kind: Diagnostic.Kind): (String) -> Unit {
        return { processingEnv.messager.printMessage(kind, it) }
    }
}
