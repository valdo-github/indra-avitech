package indra.avitechcom.entity;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USERS")
@Data
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="USER_ID", nullable=false, unique=true, length=11)
	private int id;
	
	@Column(name="USER_GUID")
	private String guid;
	
	@Column(name="USER_NAME")
	private String name;

}
