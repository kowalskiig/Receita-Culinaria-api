package com.project.miinhareceita.domains.user.domain;

import com.project.miinhareceita.favorite.domain.Favorite;
import com.project.miinhareceita.recipe.domain.Recipe;
import com.project.miinhareceita.review.domain.Review;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "tb_user")
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String lastName;

	@Column(unique = true)
	private String email;
	private String password;

	@ManyToMany
	@JoinTable(name = "tb_user_role",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@OneToMany(mappedBy = "user")
	private List<Review> reviews = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<Recipe> recipes = new ArrayList<>();

	@OneToMany(mappedBy = "id.user")
	private List<Favorite> favorites = new ArrayList<>();

	public User(Long id, String firstName, String lastName, String email, String password) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}


	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public void addRole(Role role) {
		roles.add(role);
	}

	public boolean hasRole(String roleName) {
		for (Role role : roles) {
			if (role.getAuthority().equals(roleName)) {
				return true;
			}
		}
		return false;
	}
}
	
