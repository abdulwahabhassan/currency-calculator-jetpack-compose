package com.hassan.currencycalc.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hassan.currencycalc.data.local.database.entities.RatesLocal

//data access object for Rates
@Dao
interface RatesLocalDao {
    //get rates whose base and target match the arguments
    @Query("SELECT * FROM ratesLocal WHERE target Like (:target) AND base LIKE :base")
    suspend fun getRates(base: String, target:String): RatesLocal

    //insert new rates, will return SQLite row-id of the newly inserted row
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRates(ratesLocal: RatesLocal): Long

    //update specified rates params with the corresponding arguments where the base and target params
    //of rates in the database match the base and targetList arguments, will return number of rows
    //affected by this query
    @Query("UPDATE ratesLocal SET start_date = (:startDate), end_date = (:endDate), map_of_dates_to_rates = (:mapOfDatesToRates) WHERE target LIKE (:target) AND base LIKE :base")
    suspend fun upDateRates(base: String, target: String, startDate: String, endDate: String, mapOfDatesToRates:Map<String, Map<String, Double>>): Int

    //For SELECT queries, Room will infer the result contents from the method's return type and
    //generate the code that will automatically convert the query result into the method's return
    //type.
    //
    //For single result queries, the return type can be any data object (also known as POJOs).
    //For queries that return multiple values, you can use java.util.List or Array.
    //
    //In addition to these,
    //any query may return Cursor or any query result can be wrapped in a LiveData.
    //
    //INSERT queries can return void or long. If it is a long, the value is the SQLite rowid of the
    //row inserted by this query.
    //
    //Note that queries which insert multiple rows cannot return more
    //than one rowid, so avoid such statements if returning long.
    //
    //UPDATE or DELETE queries can return void or int. If it is an int, the value is the number of
    //rows affected by this query.
    //
    //If you are using Kotlin, you can also return Flow<T> from query methods.
    //This creates a Flow<T> object that emits the results of the query and re-dispatches
    //the query every time the data in the queried table changes.
    //
    //Note that querying a table with a return type of Flow<T> always returns the first row in the
    //result set, rather than emitting all of the rows in sequence.
    //To observe changes over multiple
    //rows in a table, use a return type of Flow<List<T>> instead.
    //
    //When the return type is Flow<T>, querying an empty table throws a null pointer exception.
    //When the return type is Flow<T?>, querying an empty table emits a null value.
    //When the return type is Flow<List<T>>, querying an empty table emits an empty list.

}