package models;

import javax.persistence.Id;

public class License {
	
    @Id Long id; // Can be Long, long, or String
    
    public String email;

    public String licenseKey;

    public Long validUntil;

    public Long numberOfAccounts;

    public Long timestamp;
}
