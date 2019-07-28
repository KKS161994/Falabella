package croom.konekom.`in`

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import croom.konekom.`in`.falabella.database.AppDatabase
import croom.konekom.`in`.falabella.database.BeerEntryDao
import croom.konekom.`in`.falabella.model.Beer
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BeerTest {

    private var beerDao: BeerEntryDao? = null
    @Before
    fun setUp(){
        val db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().targetContext, AppDatabase::class.java).build()
        beerDao = db.beerDao()
    }

    @Test
    fun shouldInsertBeer() {
        val beer = Beer(
                abv = "0.05",
                ibu = "",
                id=1436,
                name = "Pub Beer",
                style = "American Pale Lager",
                ounces=12.0F
        )
        beerDao!!.insertBeer(listOf(beer))
        val headLines = beerDao!!.allBeer()
        Assert.assertEquals(beer.abv, headLines!!.value!!.get(0)?.abv)
    }

    @Test
    fun shouldFlushAllData(){
        beerDao?.nukeTable()
        Assert.assertEquals(beerDao!!.allBeer()!!.value!!.size, 0)
    }
}
