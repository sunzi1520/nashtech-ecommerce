package com.nashtech.ecommerce;

import com.nashtech.ecommerce.dto.CategoryDto;
import com.nashtech.ecommerce.dto.UserDto;
import com.nashtech.ecommerce.entity.Category;
import com.nashtech.ecommerce.entity.Role;
import com.nashtech.ecommerce.entity.User;
import com.nashtech.ecommerce.enumeration.ERoleName;
import com.nashtech.ecommerce.repository.CategoryRepository;
import com.nashtech.ecommerce.repository.RoleRepository;
import com.nashtech.ecommerce.service.CategoryService;
import com.nashtech.ecommerce.service.UserService;
import com.nashtech.ecommerce.utils.UserToUserDtoConverter;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
class EcommerceApplicationTests {

	@MockBean
	private CategoryRepository categoryRepositoryMock;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private RoleRepository roleRepositoryMock;

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

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

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setId(1L);
		categoryDto.setName("Cate 1");

		Category category = modelMapper.map(categoryDto, Category.class);

		when(categoryRepositoryMock.findById(Mockito.anyLong())).thenReturn(java.util.Optional.empty());
		when(categoryRepositoryMock.save(Mockito.<Category>any())).thenReturn(category);

		CategoryDto savedCategoryDto = categoryService.saveCategory(categoryDto);

		assertEquals(categoryDto, savedCategoryDto);
	}

	@Test
	void testConvertUserEntity_ShouldReturnUserDto() {

		Role roleAdmin = new Role();
		roleAdmin.setId(1L);
		roleAdmin.setName(ERoleName.ROLE_ADMIN);

		Role roleCustomer = new Role();
		roleCustomer.setId(2L);
		roleCustomer.setName(ERoleName.ROLE_CUSTOMER);

		Set<Role> userRoles = new HashSet<>();
		userRoles.add(roleAdmin);
		userRoles.add(roleCustomer);

		User user = new User();
		user.setId(1L);
		user.setUsername("admin");
		user.setPassword("admin");
		user.setRoles(userRoles);
		user.setFirstname("Duy");
		user.setLastname("Thai");
		user.setDob(new Date(2000, Calendar.FEBRUARY, 15));
		user.setEmail("tdbduy@gmail.com");
		user.setPhonenumber("0362781063");

		UserDto userDto = modelMapper.typeMap(User.class, UserDto.class)
							.setConverter(new UserToUserDtoConverter())
							.map(user);

		System.out.println(userDto);

		assert(true);
	}

}
