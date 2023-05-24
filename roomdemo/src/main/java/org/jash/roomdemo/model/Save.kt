package org.jash.roomdemo.model

import io.reactivex.rxjava3.schedulers.Schedulers
import org.jash.roomdemo.dao.UserDao

class Save(val userDao: UserDao) {
    fun save(user: User) {
        userDao.insert(user).subscribeOn(Schedulers.io()).subscribe({ println("保存完成")}, {it.printStackTrace()})
    }
}