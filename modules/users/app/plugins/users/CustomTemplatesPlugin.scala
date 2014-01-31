package plugins.users

import securesocial.controllers.DefaultTemplatesPlugin
import play.api.Application
import play.api.mvc.{RequestHeader, Request}
import securesocial.controllers.Registration.RegistrationInfo
import securesocial.core.{Identity, SecuredRequest}
import securesocial.controllers.PasswordChange.ChangeInfo
import play.api.data._
import play.api.i18n.Messages
import play.api.libs.json.{JsObject, Json}
import play.api.templates.{Txt, Html}
import models.users.sorm.SormUserDb


class CustomTemplatesPlugin(application: Application) extends DefaultTemplatesPlugin(application) {
  implicit val changeInfoFormat = Json.format[ChangeInfo]
  implicit val registrationInfoFormat = Json.format[RegistrationInfo]

  def toHtml(obj: JsObject): Html = {
    Html(Json.stringify(obj))
  }

  override def getLoginPage[A](implicit request: Request[A], form: Form[(String, String)], errorMsg: Option[String] = None): Html = {
    errorMsg.map( message =>
      toHtml(Json.obj(
        "message" -> Messages(message)
      ))
    ).getOrElse(
      if (form.hasErrors) {
        Html(form.errorsAsJson.toString())
      } else {
        var username: String = ""
        var password: String = ""

        form.value.map(g => {
          username = g._1
          password = g._2
        })

        toHtml(Json.obj(
          "username" -> username,
          "password" -> password
        ))
      }
    )
  }

  override def getStartSignUpPage[A](implicit request: Request[A], form: Form[String]): Html = {
    if (form.hasErrors)
      Html(form.errorsAsJson.toString())
    else {
      var email: String = ""

      form.value.map(e => email = e)

      toHtml(Json.obj(
        "form" -> Json.obj(
          "email" -> email
        )
      ))
    }
  }

  override def getSignUpPage[A](implicit request: Request[A], form: Form[RegistrationInfo], token: String): Html = {
    if (form.hasErrors) {
      Html(form.errorsAsJson.toString())
    } else {
      val email = SormUserDb.findEmailByTokenUuid(token).getOrElse("")

      var registrationInfo: RegistrationInfo = RegistrationInfo(None, "", "", "")

      form.value.map(r => registrationInfo = r)

      toHtml(Json.obj(
          "email" -> email,
          "form" -> registrationInfo,
          "token" -> token
        ))
    }
  }

  override def getStartResetPasswordPage[A](implicit request: Request[A], form: Form[String]): Html = {
    toHtml(Json.obj(
      "email" -> form.value
    ))
  }

  override def getResetPasswordPage[A](implicit request: Request[A], form: Form[(String, String)], token: String): Html = {
//    form.value.map(v =>
//      toHtml(Json.obj(
//        "form" -> Json.obj(
//          "password.password1" -> v._1,
//          "password.password2" -> v._2
//        ),
//        "token" -> token
//      ))
//    ).getOrElse(
//
//      )
    Html("")
  }

  override def getPasswordChangePage[A](implicit request: SecuredRequest[A], form: Form[ChangeInfo]):Html = {
    toHtml(Json.obj(
      "email" -> form.get
    ))
  }

  override def getSignUpEmail(token: String)(implicit request: RequestHeader): (Option[Txt], Option[Html]) = {
    (None, Option(Html("Go to http://" + request.host + "/#/signup/" + token)))
  }
}