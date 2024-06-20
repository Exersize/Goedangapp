import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.goedangapp.repository.GoedangRepo

class DashboardViewModel(private val repository: GoedangRepo) : ViewModel() {

    fun getLowInStock() = repository.filterLowStock()
    fun getUser() = repository.getUser().asLiveData()
    fun getItemById(id: String) = repository.getItemById(id)
    fun getSortedItemEntries() = repository.filterRecentItemEntry()
}