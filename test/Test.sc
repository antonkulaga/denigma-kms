import com.github.t3hnar.bcrypt._
val p ="bcrypt$$2a$12$In8PQmveHxjT4.rbZVrogeaybH466fwPs.OQdcLElvV6Tuz0GUG1K"
val hash = "$2a$12$In8PQmveHxjT4.rbZVrogeaybH466fwPs.OQdcLElvV6Tuz0GUG1K"
"brain2brain".isBcrypted(hash)
"mind2@mind".isBcrypted(hash)
"mind2mind".isBcrypted(hash)

