package models.common

import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
 * Created by jd on 1/27/14.
 */
case class Navigation(page: String, left: Seq[NavigationMenu], right: Seq[NavigationMenu] = Nil)
case class NavigationMenu(items: Seq[NavigationItem], dropDown: Boolean = false)
case class NavigationItem(display: String, route: String)

object Navigation {
  //implicit val navigationMenuReads = Json.reads[NavigationMenu]
  implicit val navigationItemWrites: Writes[NavigationItem] = (
    (__ \ "display").write[String] and
      (__ \ "route").write[String]
    )(unlift(NavigationItem.unapply))

  implicit val navigationMenuWrites: Writes[NavigationMenu] = (
    (__ \ "items").lazyWrite(Writes.traversableWrites[NavigationItem](navigationItemWrites)) and
      (__ \ "dropDown").write[Boolean]
    )(unlift(NavigationMenu.unapply))

  //implicit val navigationReads = Json.reads[Navigation]
  implicit val navigationWrites: Writes[Navigation] = (
    (__ \ "page").write[String] and
    (__ \ "left").lazyWrite(Writes.traversableWrites[NavigationMenu](navigationMenuWrites)) and
      (__ \ "right").lazyWrite(Writes.traversableWrites[NavigationMenu](navigationMenuWrites))
    )(unlift(Navigation.unapply))

  def toJson(navigation: Navigation): String = {
    Json.stringify(Json.toJson(navigation))
  }
}