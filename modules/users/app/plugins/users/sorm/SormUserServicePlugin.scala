package plugins.users.sorm

import play.api.Application
import models.users.sorm.SormUserDb
import plugins.users.UserServicePlugin

/**
 * Created by honaink on 1/19/14.
 */
class SormUserServicePlugin(application: Application) extends UserServicePlugin(application) {
  val userDb = SormUserDb
}
