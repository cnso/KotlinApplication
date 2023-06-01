package org.jash.common.annotations
@Retention
@Target(AnnotationTarget.PROPERTY_SETTER)
annotation class BindingLayout(
    val resName:String
)
