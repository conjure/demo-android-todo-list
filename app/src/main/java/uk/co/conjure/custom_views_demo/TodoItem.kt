package uk.co.conjure.custom_views_demo

data class TodoItem(
    val id: Int,
    val name: String,
    val priority: Int,
    val done: Boolean
)