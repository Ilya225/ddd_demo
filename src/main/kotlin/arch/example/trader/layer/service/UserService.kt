package arch.example.trader.layer.service

import arch.example.trader.layer.dto.PatchUserDto
import arch.example.trader.layer.dto.UserDto
import arch.example.trader.layer.entity.User
import arch.example.trader.layer.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(
    val userRepository: UserRepository
) {

    fun register(userDto: UserDto): UUID {

        val user = User(
            userDto.id,
            userDto.username,
            userDto.email,
            userDto.password,
        )

        if (userRepository.existsById(userDto.id)) {
            throw IllegalArgumentException("User is already exists")
        }

        return userRepository.save(user).id
    }

    fun updateUser(userDto: PatchUserDto, userId: UUID): UUID {
        return userRepository.findById(userId)
            .map {
                it.username = userDto.username ?: it.username
                it.email = userDto.email ?: it.email
                userRepository.save(it).id
            }.orElseThrow { throw IllegalStateException("User doesn't exist") }
    }
}