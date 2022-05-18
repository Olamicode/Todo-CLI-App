package com.ola.todocliapplication

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.annotation.VisibleForTesting.PROTECTED
import com.ola.todocliapplication.TodoApp.Companion.printMenu
import com.ola.todocliapplication.TodoApp.Companion.processChoice

class TodoApp {

    companion object {
        private const val TASK_ONE = "1. Enter new task"
        private const val TASK_TWO = "2. Complete task"
        private const val TASK_THREE = "3. Print all tasks"
        private const val TASK_FOUR = "4. Close App"

        @VisibleForTesting(otherwise = PRIVATE)
        const val OPTION_ONE = "1"
        @VisibleForTesting(otherwise = PRIVATE)
        const val OPTION_TWO = "2"
        @VisibleForTesting(otherwise = PRIVATE)
        const val OPTION_THREE = "3"
        @VisibleForTesting(otherwise = PROTECTED)
        const val OPTION_FOUR = "4"

        @VisibleForTesting(otherwise = PRIVATE)
        fun addTodo(todo: Todo) {
            TodoStorage.store.add(todo)
        }

        @VisibleForTesting(otherwise = PRIVATE)
        fun deleteTodo(todo: Todo) {
            TodoStorage.store.remove(todo)
        }
        @VisibleForTesting(otherwise = PRIVATE)
        fun readAllTodoTasks() {
            TodoStorage.store.forEachIndexed { index, todo ->
                println("${index + 1}. ${todo.task}")
            }
        }

        private fun printMenuWithPartition() {
            println("=========================")
            printMenu()
        }

        fun printMenu() {
            val menu = listOf(TASK_ONE, TASK_TWO, TASK_THREE, TASK_FOUR)
            menu.forEach {
                println(it)
            }
        }

        fun processChoice(chosenOption: String) {

            if (chosenOption == OPTION_ONE) {
                println("Enter new task: ")
                val newTask = readLine().toString()
                val todo = Todo(newTask)
                addTodo(todo)
                println("=========================")
                printMenu()

            } else if (chosenOption == OPTION_TWO) {

                println("Enter the task you want to complete:")
                val taskToBeCompleted = readLine().toString()
                val todo = Todo(taskToBeCompleted)

                if (TodoStorage.store.contains(todo)) {
                    deleteTodo(todo)
                    println("$taskToBeCompleted - task completed successfully!")
                } else {
                    println("Task not available in Todo.")
                }
                printMenuWithPartition()

            } else if (chosenOption == OPTION_THREE) {

                if (TodoStorage.store.isEmpty()) {
                    println("There is no task for you to do")
                } else {
                    println("These are all your remaining tasks: =========================")
                    readAllTodoTasks()
                    println(" ")
                    printMenuWithPartition()
                }
            } else {
                println("Not a valid option")
                printMenuWithPartition()
            }
        }
    }

}

fun main() {
    printMenu()

    var chosenOption = readLine().toString()

    while (chosenOption != TodoApp.OPTION_FOUR) {
        processChoice(chosenOption)
        chosenOption = readLine().toString()
    }

    if (chosenOption == TodoApp.OPTION_FOUR) {
        println("Application closed!!!!")
    }
}