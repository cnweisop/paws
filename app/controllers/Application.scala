package controllers

import play.api.mvc.{Action, Controller}
import models.{Navigation, NavigationItem, NavigationMenu}


object Application extends Controller {
  def navigation() = Action {
    implicit request =>
    val left = NavigationMenu(
      Seq(
        NavigationItem("PAWS", "/"),
        NavigationItem("Sign Up", "#/signup"),
        NavigationItem("Sign In", "#/login")
      )
    )

    val right = NavigationMenu(
      Seq(
        NavigationItem("Sign Out", "#/signout")
      )
    )

    val navigation = Navigation(Seq(left), Seq(right))

    Ok(Navigation.toJson(navigation))
  }
}
