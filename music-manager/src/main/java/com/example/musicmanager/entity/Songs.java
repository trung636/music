package com.example.musicmanager.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Entity
@Table(name = "songs", uniqueConstraints = { @UniqueConstraint(columnNames = "id_song") })
@NoArgsConstructor
public class Songs {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_song", unique = true, nullable = false)
	private int idSong;

	@NotNull(message = "not null")
	@Size(min = 1, max = 20, message = "between 1 and 20")
	@Column(name = "artist", unique = false, nullable = false)
	private String artist;

	@ManyToOne
	@JoinColumn(name = "id_user", nullable = false)
	@JsonIgnore
	private User user;

	@NotNull(message = "not null")
	@Size(min = 1, max = 20, message = "between 1 and 20")
	@Column(name = "name_song", unique = false, nullable = false)
	private String nameSong;

	@NotNull(message = "not null")
	@Size(min=2)
	@Column(name = "code_song", unique = false, nullable = false)
	private String codeSong;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time")
	private Date updateTime;


}
