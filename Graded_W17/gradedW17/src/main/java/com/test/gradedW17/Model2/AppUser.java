package com.test.gradedW17.Model2;

import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser {
	
	@Id
	private int id;
	@Column (unique = true)
	private String name;
	private String username;
	private String password;
	
	@ElementCollection (fetch = FetchType.EAGER)
	@CollectionTable (name = "user_authorities", joinColumns = @JoinColumn(name = "user_id"))
	private Set<String> roles;
}
