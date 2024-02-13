package com.prography.pingpong.system.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prography.pingpong.common.dto.ApiResponse;
import com.prography.pingpong.common.dto.CreateUserDto;
import com.prography.pingpong.common.dto.InitRequest;
import com.prography.pingpong.room.repository.RoomRepository;
import com.prography.pingpong.room.repository.UserRoomRepository;
import com.prography.pingpong.user.entity.User;
import com.prography.pingpong.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ServerServiceImpl implements ServerService{

    private final UserRepository userRepository;

    private final UserRoomRepository userRoomRepository;

    private final RoomRepository roomRepository;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;
    @Override
    public ApiResponse<Object> createUser(InitRequest initRequest) {
        ApiResponse<Object> response = new ApiResponse<>(200,"API 요청이 성공했습니다.", null );
        deleteAllUserAndRoom();
        try {
            // URL 템플릿에 사용될 변수를 맵에 저장 -> service로 옮기는 것을 고려
            Map<String, Object> uriVariables = new HashMap<>();
            uriVariables.put("seed", initRequest.getSeed());
            uriVariables.put("quantity", initRequest.getQuantity());

            String url = "https://fakerapi.it/api/v1/users?_seed={seed}&_quantity={quantity}&_locale=ko_KR";

            ResponseEntity<String> apiResponse = restTemplate.getForEntity(url, String.class, uriVariables);

            if( apiResponse.getStatusCode().value() != 200){
                response = new ApiResponse<>(201,"불가능한 요청입니다.", null );
            }

            JsonNode rootNode =  objectMapper.readTree(apiResponse.getBody());
            JsonNode dataArray = rootNode.path("data");
            if (dataArray.isArray()) {
                for (JsonNode dataNode : dataArray) {
                    String id = dataNode.path("id").asText();
                    String username = dataNode.path("username").asText();
                    String email = dataNode.path("email").asText();
                    CreateUserDto createUserDto = new CreateUserDto();
                    createUserDto.setId(Integer.parseInt(id));
                    createUserDto.setUsername(username);
                    createUserDto.setEmail(email);
                    userRepository.save(User.from(createUserDto));
                }
            }else{
                response = new ApiResponse<>(201,"불가능한 요청입니다.", null );
            }
        } catch (JsonProcessingException e) {
            response = new  ApiResponse<>(500,"에러가 발생했습니다.", null );
            throw new RuntimeException(e);
        }
        return response;
    }

    private void deleteAllUserAndRoom() {
        userRepository.deleteAllInBatch();
        userRoomRepository.deleteAllInBatch();
        roomRepository.deleteAllInBatch();
    }
}
