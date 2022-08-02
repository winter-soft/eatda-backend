package com.proceed.swhackathon.service;

import com.proceed.swhackathon.dto.store.StoreUpdateDTO;
import com.proceed.swhackathon.exception.store.StoreNotFoundException;
import com.proceed.swhackathon.exception.user.UserNotFoundException;
import com.proceed.swhackathon.model.Likes;
import com.proceed.swhackathon.model.Store;
import com.proceed.swhackathon.model.User;
import com.proceed.swhackathon.repository.LikesRepository;
import com.proceed.swhackathon.repository.StoreRepository;
import com.proceed.swhackathon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikesService {
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final LikesRepository likesRepository;

    @Transactional
    public String clickLikes(String userId, Long storeId){
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        Store store = storeRepository.findById(storeId).orElseThrow(() -> {
            throw new StoreNotFoundException();
        });

        Likes likes = likesRepository.findByStoreAndUser(store, user);
        if(likes != null){
            likes.deleteLike();
            likesRepository.delete(likes);
            return "좋아요 취소";
        }else{
            likes = likesRepository.save(Likes.builder()
                            .user(user)
                            .store(store)
                            .build());
            likes.addLike();
            return "좋아요 완료";
        }
    }

    public List<StoreUpdateDTO> likesList(String userId){
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        return likesRepository.findByUser(user)
                .stream()
                .map(likes -> StoreUpdateDTO.entityToDTO(likes.getStore()))
                .collect(Collectors.toList());
    }
}
