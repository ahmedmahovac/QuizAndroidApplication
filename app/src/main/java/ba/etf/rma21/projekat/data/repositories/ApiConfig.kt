package ba.etf.rma21.projekat.data.repositories

class ApiConfig {
    companion object {
         var baseURL = "https://rma21-etf.herokuapp.com"



        suspend fun postaviBaseURL(baseUrl: String) : Unit {
            this.baseURL = baseUrl
        }

    }
}