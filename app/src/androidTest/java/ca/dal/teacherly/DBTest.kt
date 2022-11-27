package ca.dal.teacherly

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import ca.dal.teacherly.utils.DatabaseSingleton
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DBTest {

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("ca.dal.teacherly", appContext.packageName)
    }

    @Test
    fun testUserDBReference() {

        // Obtain user Reference
        val ref = DatabaseSingleton.getUserReference()

        // Validate if the same object is returned
        Assert.assertEquals(ref, DatabaseSingleton.getUserReference())
    }

    @Test
    fun testSubjectDBReference() {

        // Obtain subject Reference
        val ref = DatabaseSingleton.getSubjectsReference()

        // Validate if the same object is returned
        Assert.assertEquals(ref, DatabaseSingleton.getSubjectsReference())
    }

    @Test
    fun testBookingDBReference() {

        // Obtain booking Reference
        val ref = DatabaseSingleton.getBookingsReference()

        // Validate if the same object is returned
        Assert.assertEquals(ref, DatabaseSingleton.getBookingsReference())
    }

    @Test
    fun testAssignmentDBReference() {

        // Obtain assignment Reference
        val ref = DatabaseSingleton.getAssignmentReference()

        // Validate if the same object is returned
        Assert.assertEquals(ref, DatabaseSingleton.getAssignmentReference())
    }
}