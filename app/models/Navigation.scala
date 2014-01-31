package models

import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
 * Created by jd on 1/27/14.
 */
case class Navigation(left: Seq[NavigationMenu], right: Seq[NavigationMenu] = Nil)

case class NavigationMenu(items: Seq[NavigationItem], dropDown: Boolean = false)

case class NavigationItem(name: String, route: String)

object Navigation {
  implicit val navigationItemFormat = Json.format[NavigationItem]

  implicit val navigationMenuReads = Json.reads[NavigationMenu]
  implicit val navigationMenuWrites: Writes[NavigationMenu] = (
    (__ \ "items").lazyWrite(Writes.traversableWrites[NavigationItem](navigationItemFormat)) and
      (__ \ "dropDown").write[Boolean]
    )(unlift(NavigationMenu.unapply))

  implicit val navigationReads = Json.reads[Navigation]
  implicit val navigationWrites: Writes[Navigation] = (
    (__ \ "left").lazyWrite(Writes.traversableWrites[NavigationMenu](navigationMenuWrites)) and
      (__ \ "right").lazyWrite(Writes.traversableWrites[NavigationMenu](navigationMenuWrites))
    )(unlift(Navigation.unapply))

  def toJson(navigation: Navigation): String = {
    Json.stringify(Json.toJson(navigation))
  }
}