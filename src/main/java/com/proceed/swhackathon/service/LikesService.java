package com.proceed.swhackathon.service;

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

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikesService {
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final LikesRepository likesRepository;

    @Transactional
    public void clickLikes(String userId, Long storeId){
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
        }else{
            likes = likesRepository.save(Likes.builder()
                            .user(user)
                            .store(store)
                            .build());
            likes.addLike();
        }
    }
}
