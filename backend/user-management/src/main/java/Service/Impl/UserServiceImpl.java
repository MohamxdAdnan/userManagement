package Service.Impl;

import Dto.UserDto;
import Entity.User;
import Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import Service.UserService;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        User newUser = userRepository.save(user);
        return modelMapper.map(newUser,UserDto.class);
    }


    @Override
    public UserDto updateUser(UserDto userDto,Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id:" + userId));
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setFullName(userDto.getFullName());
        user.setAge(userDto.getAge());

        User newUser = userRepository.save(user);
        return modelMapper.map(newUser,UserDto.class);

    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id:" + userId));
        userRepository.delete(user);

    }

    @Override
    public UserDto getUser(Long userId) {
        User employee = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id:" + userId));

        return modelMapper.map(employee,UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        List<UserDto> allUserDtos = allUsers.stream()
                .map((user)->modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        return allUserDtos;
    }
}
