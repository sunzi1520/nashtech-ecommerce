package com.nashtech.ecommerce;

import com.nashtech.ecommerce.entity.Category;
import com.nashtech.ecommerce.repository.CategoryRepository;
import com.nashtech.ecommerce.service.CategoryService;
import com.nashtech.ecommerce.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
class EcommerceApplicationTests {

	@MockBean
	private CategoryRepository categoryRepositoryMock;

	@Autowired
	private CategoryService categoryService;

	@BeforeAll
	public static void setUp() {
		System.out.println("Start test!");
	}

	@BeforeEach
	public void beforeEach() {
		System.out.println("Before each test");
	}

	@AfterEach
	public void afterEach() {
		System.out.println("After each test");
	}

	@AfterAll
	public static void  tearDown() {
		System.out.println("End test!");
	}

	@Test
	void testSaveCategory_WhenNameNotExisting_ShouldReturnCreated() {
		assertNotNull(categoryRepositoryMock);

		Category category = new Category();
		category.setId(1L);
		category.setName("Cate 1");

		//when(categoryRepositoryMock.findById(Mockito.anyLong())).thenReturn(java.util.Optional.empty());
		when(categoryRepositoryMock.save(Mockito.<Category>any())).thenReturn(category);

		Category savedCategory = categoryService.saveCategory(category);

		assertEquals(category, savedCategory);
	}

}
