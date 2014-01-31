package models.users.sorm

import securesocial.core._
import org.joda.time.DateTime
import securesocial.core.IdentityId
import securesocial.core.providers.Token
import models.users.UserDb


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
    SormDb.query[SocialUser].whereEqual("email", identity.email).fetchOne().getOrElse(
      SormDb.transaction {
        SormDb.save(SocialUser(
          SormDb.save(identity.identityId),
          identity.firstName,
          identity.lastName,
          identity.fullName,
          identity.email,
          identity.avatarUrl,
          SormDb.save(identity.authMethod),
          identity.oAuth1Info.map(e => SormDb.save(e)),
          identity.oAuth2Info.map(e => SormDb.save(e)),
          identity.passwordInfo.map(e => SormDb.save(e))
        ))
      }
    )
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
    SormDb.query[Token].whereSmallerOrEqual("expirationTime", DateTime.now()).fetch() foreach { SormDb.delete(_) }
  }

  def findAll:List[SocialUser] = {
    SormDb.query[SocialUser].fetch().toList
  }

  def findEmailByTokenUuid(tokenUuid: String): Option[String] = {
    SormDb.query[Token].whereEqual("uuid", tokenUuid).fetchOne().map(t => t.email)
  }
}