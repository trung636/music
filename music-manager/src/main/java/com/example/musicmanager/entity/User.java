package com.example.musicmanager.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Entity
@Table(name = "user", uniqueConstraints = { @UniqueConstraint(columnNames = "id_user") })
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user", unique = true, nullable = false)
	private int idUser;

	@Size(min = 1, max = 255, message = "username between 1 and 255")
	@Column(name = "username", unique = true, nullable = false)
	private String username;

	@Size(min = 1, max = 255, message = "password between 1 and 255")
	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "role", nullable = false)
	private String role;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time")
	private Date updateTime;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL ,mappedBy = "user")
	private Set<Songs> songs = new HashSet<>();

}
