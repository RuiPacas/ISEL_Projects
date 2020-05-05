package pt.isel.bgg.viewmodel

import androidx.lifecycle.*
import pt.isel.bgg.app.BGGApp
import pt.isel.bgg.model.favorite.Mechanic

class MultipleMechanicsChoicesViewModel() : ViewModel() {
    private var liveData: LiveData<List<Mechanic>> = MutableLiveData()

    val mechanics: List<Mechanic>
        get() = liveData.value ?: emptyList()

    fun getMechanics() {
        BGGApp.bggWebApi.getMechanics(
            { it.forEach { x -> BGGApp.bggDb.mechanicDao().insertMechanic(x) } },
            { throw it }
        )
        liveData = BGGApp.bggDb.mechanicDao().getAllMechanics()
    }

    fun observe(owner: LifecycleOwner, observer: (List<Mechanic>) -> Unit) {
        liveData.observe(owner, Observer { observer(it) })
    }
}
