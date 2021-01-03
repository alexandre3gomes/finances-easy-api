package com.finances.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

public class BaseAuditEntity {

	@CreatedBy
	@JsonIgnore
	private User createdBy;
	@CreatedDate
	@JsonIgnore
	private DateTime createdDate;
	@LastModifiedBy
	@JsonIgnore
	private User lastModifiedBy;
	@LastModifiedDate
	@JsonIgnore
	private DateTime lastModifiedDate;

}
