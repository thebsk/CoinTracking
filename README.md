# CoinTracking

Application fetches data from Crypto Currency API - Coingecko via Retrofit and saves it locally via Room. Then it retrieves data from local database.
Project is written in Kotlin by appling architecture pattern of MVVM. Dagger 2 is used for dependency injection.
<hr/>

<h3> Applied technologies and libraries: </h3>
<ul>
<li><h3>Model</h3>

<h4><i>Retrofit 2</i></h4> - Getting data from server into pojo-classes
<h4><i>SQLite</i></h4> - Storing data fetched from server
<h4><i>Coroutines</i></h4> 
   - Managing asynchronous database and network queries<br/>
   - Providing light asynchronous operations
</li>	 
<li><h3>ViewModel</h3>
<h4><i>LiveData</i></h4> - Observer-pattern implementation for View interaction
</li>

<li><h3>View</h3>
<h4><i>Data Binding</i></h4>
   - Replace basic operations with UI (e.g. findViewById() ) to the XML
   
<h4><i>Glide</i></h4>
   - Image loading with the help of Data Binding
</li>
</ul>
