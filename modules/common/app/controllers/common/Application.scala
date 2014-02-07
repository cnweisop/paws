package controllers.common

import play.api.mvc.{Action, Controller}
import models.common.{NavigationItem, NavigationMenu, Navigation}


object Application extends Controller {
  def navigation() = Action {
    implicit request =>
    val left =
      Seq(
        NavigationMenu(
          Seq(
            NavigationItem("PAWS", "/"),
            NavigationItem("Sign Up", "#/signup"),
            NavigationItem("Sign In", "#/login")
          )
        )
      )

    val right =
      Seq(
        NavigationMenu(
          Seq(
            NavigationItem("Sign Out", "#/signout")
          )
        )
      )

    val navigation = Navigation("default", left, right)

    Ok(Navigation.toJson(navigation))
  }
}
