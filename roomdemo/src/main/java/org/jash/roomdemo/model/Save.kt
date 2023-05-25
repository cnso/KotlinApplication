package org.jash.roomdemo.model

import io.reactivex.rxjava3.schedulers.Schedulers
import org.jash.roomdemo.GoodService
import org.jash.roomdemo.dao.UserDao
import org.jash.roomdemo.logd
import org.jash.roomdemo.proscessor
import org.jash.roomdemo.retrofit

class Save(val userDao: UserDao) {
    fun save(user: User) {
        val service = retrofit.create(GoodService::class.java)
        service.login(user).subscribe {
            if (it.code == 200) {
                proscessor.onNext(it.data)
            } else {
                proscessor.onNext(it.message)
            }
        }

//        userDao.insert(user).subscribeOn(Schedulers.io()).subscribe({ println("保存完成")}, {it.printStackTrace()})
    }
}