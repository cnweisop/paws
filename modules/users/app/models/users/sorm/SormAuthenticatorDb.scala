package models.users.sorm

import securesocial.core.{Authenticator, IdentityId}
import models.users.AuthenticatorDb
import plugins.users.AuthenticatorEntity


object SormAuthenticatorDb extends AuthenticatorDb {
  def save(authenticator: Authenticator): Either[Error, Unit] = {
    val identityId = SormDb.query[IdentityId]
      .whereEqual("providerId", authenticator.identityId.providerId)
      .whereEqual("userId", authenticator.identityId.userId)
      .fetchOne().getOrElse(SormDb.save(authenticator.identityId))

    SormDb.save(AuthenticatorEntity(
      authenticator.id,
      identityId,
      authenticator.creationDate,
      authenticator.lastUsed,
      authenticator.expirationDate
    ))
    Right(())
  }

  def find(key: String): Either[Error, Option[Authenticator]] = {
    Right(SormDb.query[AuthenticatorEntity].whereEqual("key", key).fetchOne().map(e =>
      Authenticator(e.key, e.identityId, e.creationDate, e.lastUsed, e.expirationDate)
    ))
  }

  def findEntity(key: String): Option[AuthenticatorEntity] = {
    SormDb.query[AuthenticatorEntity].whereEqual("key", key).fetchOne()
  }

  def delete(key: String): Either[Error, Unit] = {
    SormDb.query[AuthenticatorEntity].whereEqual("key", key).fetchOne().map(e => SormDb.delete(e))
    Right(())
  }
}
