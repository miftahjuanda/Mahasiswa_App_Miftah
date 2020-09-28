import com.udacoding.mahasiswa_app_miftah.network.NetworkModule
import com.udacoding.mahasiswa_app_miftah.ui.register.presenter.RegisterView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class RegisterPresenter(val registerView: RegisterView) {

    fun getRegistrasi(nama: String, email: String, password: String, passwordConfirm: String) {

        if (password != passwordConfirm) {
            registerView.progresVisible()
            registerView.passwordConfirm()
            if (password.length <= 6) {
                registerView.errorRegister("Password minimal 6 karakter")
                registerView.progresGone()
            } else {
                registerView.progresGone()
                NetworkModule.service().getRegister(nama, email, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        registerView.progresGone()
                        val message = it.message
                        if (it.isSuccess ?: true) {
                            registerView.successRegister(it)
                        } else {
                            registerView.errorRegister(message ?: "")
                        }
                    }, {
                        registerView.errorRegister(it.localizedMessage)
                    })
            }
        }
    }
}