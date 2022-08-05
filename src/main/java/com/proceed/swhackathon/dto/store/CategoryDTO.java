package com.proceed.swhackathon.dto.store;

import com.proceed.swhackathon.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    Category category;
    String category_ko;
}
