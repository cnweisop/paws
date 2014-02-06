package models.users.sorm

import securesocial.core._
import org.joda.time.DateTime
import securesocial.core.IdentityId
import securesocial.core.providers.Token
import models.users.UserDb
import sorm.Persisted


object SormUserDb extends UserDb {
  def find(identityId: IdentityId): Option[SocialUser] = {
    SormDb.query[SocialUser]
      .whereEqual("identityId.userId", identityId.userId)
      .whereEqual("identityId.providerId", identityId.providerId)
      .fetchOne()
  }

  def findByEmailAndProvider(email: String, providerId: String): Option[SocialUser] = {
    SormDb.query[SocialUser]
      .whereEqual("email", Option(email))
      .whereEqual("identityId.providerId", providerId)
      .fetchOne()
  }

  def save(identity: Identity): SocialUser = {
    val socialUser: Option[SocialUser with Persisted] = SormDb.query[SocialUser].whereEqual("email", identity.email).fetchOne()
    socialUser match {
      case None => val newUser = SocialUser(
        SormDb.save(identity.identityId),
        identity.firstName,
        identity.lastName,
        identity.fullName,
        identity.email,
        identity.avatarUrl,
        SormDb.save(identity.authMethod),
        identity.oAuth1Info.map(e => SormDb.save(e)),
        identity.oAuth2Info.map(e => SormDb.save(e)),
        identity.passwordInfo.map(e => SormDb.save(e)))
        SormDb.transaction {
          SormDb.save(newUser)
        }
        newUser
      case Some(existingUser) =>
        val passInfo = Some(SormDb.save(identity.passwordInfo.get))
        val updatedUser = existingUser.copy(identityId = identity.identityId, firstName = identity.firstName,
        lastName = identity.lastName, fullName = identity.fullName, email = identity.email, avatarUrl = identity.avatarUrl, authMethod = identity.authMethod,
        oAuth1Info = identity.oAuth1Info, oAuth2Info = identity.oAuth2Info, passwordInfo = passInfo)
        SormDb.transaction {
          SormDb.save(updatedUser)
        }
        updatedUser
    }
  }

  def save(token: Token) = {
    SormDb.save(token)
  }

  def findToken(uuid: String): Option[Token] = {
    SormDb.query[Token].whereEqual("uuid", uuid).fetchOne()
  }

  def deleteToken(uuid: String) {
    findToken(uuid).map(e => SormDb.delete(e))
  }

  def deleteExpiredTokens() {
    SormDb.query[Token].whereSmallerOrEqual("expirationTime", DateTime.now()).fetch() foreach {
      SormDb.delete(_)
    }
  }

  def findAll: List[SocialUser] = {
    SormDb.query[SocialUser].fetch().toList
  }

  def findEmailByTokenUuid(tokenUuid: String): Option[String] = {
    SormDb.query[Token].whereEqual("uuid", tokenUuid).fetchOne().map(t => t.email)
  }
}