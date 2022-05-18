package com.ola.todocliapplication

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class TodoAppTest {

    lateinit var todoOne: Todo

    @Before
    fun setUp() {
        todoOne = Todo("Task one")
        TodoApp.addTodo(todoOne)
    }

    @Test
    fun testAddTodo(){
        assertTrue(TodoStorage.store.contains(todoOne))
    }

    @Test
    fun testReadAllTodo() {
        val outputStreamCaptor =  ByteArrayOutputStream()
        System.setOut(PrintStream(outputStreamCaptor))

        TodoApp.readAllTodoTasks()
        assertTrue( outputStreamCaptor.toString().lines().contains("1. Task one"))
    }

    @Test
    fun testDeleteTodo() {
        TodoApp.deleteTodo(todoOne)
        assertTrue(!TodoStorage.store.contains(todoOne))
    }

    @Test
    fun testPrintMenu() {
        val outputStreamCaptor =  ByteArrayOutputStream()
        System.setOut(PrintStream(outputStreamCaptor))

        TodoApp.printMenu()
        assertEquals("1. Enter new task\n" +
                "2. Complete task\n" +
                "3. Print all tasks\n" +
                "4. Close App", outputStreamCaptor.toString().trim())
    }

    @Test
    fun testProcessOptionWithOptionOne() {
        val outputStreamCaptor =  ByteArrayOutputStream()
        System.setOut(PrintStream(outputStreamCaptor))

        TodoApp.processChoice(TodoApp.OPTION_ONE)
        assertEquals("Enter new task: \n" +
                "=========================\n" +
                "1. Enter new task\n" +
                "2. Complete task\n" +
                "3. Print all tasks\n" +
                "4. Close App", outputStreamCaptor.toString().trim())
    }

    @Test
    fun testProcessOptionWithOptionTwoWithNoTask() {
        val outputStreamCaptor =  ByteArrayOutputStream()
        System.setOut(PrintStream(outputStreamCaptor))

        TodoApp.processChoice(TodoApp.OPTION_TWO)

        assertEquals("Enter the task you want to complete:\n" +
                "Task not available in Todo.\n" +
                "=========================\n" +
                "1. Enter new task\n" +
                "2. Complete task\n" +
                "3. Print all tasks\n" +
                "4. Close App", outputStreamCaptor.toString().trim())
    }

    @Test
    fun testProcessOptionWithOptionThreeWhenStoreIsNotEmpty() {
        val outputStreamCaptor =  ByteArrayOutputStream()
        System.setOut(PrintStream(outputStreamCaptor))

        TodoApp.processChoice(TodoApp.OPTION_THREE)

        assertEquals("These are all your remaining tasks: =========================\n" +
                "1. Task one\n" +
                " \n" +
                "=========================\n" +
                "1. Enter new task\n" +
                "2. Complete task\n" +
                "3. Print all tasks\n" +
                "4. Close App", outputStreamCaptor.toString().trim())
    }

    @Test
    fun testProcessOptionWithOptionThreeWhenStoreIsEmpty() {
        val outputStreamCaptor =  ByteArrayOutputStream()
        System.setOut(PrintStream(outputStreamCaptor))

        TodoApp.deleteTodo(todoOne)

        TodoApp.processChoice(TodoApp.OPTION_THREE)

        assertEquals("There is no task for you to do", outputStreamCaptor.toString().trim())
    }

    @Test
    fun testProcessOptionWithInvalidOption() {
        val outputStreamCaptor =  ByteArrayOutputStream()
        System.setOut(PrintStream(outputStreamCaptor))

        TodoApp.processChoice("Invalid Option")

        assertEquals("Not a valid option\n" +
                "=========================\n" +
                "1. Enter new task\n" +
                "2. Complete task\n" +
                "3. Print all tasks\n" +
                "4. Close App", outputStreamCaptor.toString().trim())
    }

}