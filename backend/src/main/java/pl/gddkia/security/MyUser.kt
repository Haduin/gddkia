package pl.gddkia.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class MyUser(username: String?, password: String?, authorities: Collection<GrantedAuthority?>?) :
    User(username, password, authorities)
