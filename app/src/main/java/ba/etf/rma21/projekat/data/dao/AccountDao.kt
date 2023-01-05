package ba.etf.rma21.projekat.data.dao

import androidx.room.*
import ba.etf.rma21.projekat.data.models.Account

@Dao
interface AccountDao {

    @Query("SELECT lastUpdate FROM Account limit 1")
    suspend fun getLastUpdate() : String

    @Query("UPDATE ACCOUNT SET LASTUPDATE = :lastUpdateParam")
    suspend fun setLastUpdate(lastUpdateParam: String)

    @Insert
    suspend fun setAccount(account :  Account)

    @Query("DELETE FROM ACCOUNT")
    suspend fun deleteUser()

    @Query("SELECT acHash FROM Account")
    suspend fun getHash() : String



    @Query("SELECT COUNT(*) FROM ACCOUNT")
    suspend fun getCount() : Int

}