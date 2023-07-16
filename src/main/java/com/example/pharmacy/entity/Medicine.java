package com.example.pharmacy.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Medicine extends BaseEntity {
    private String name;
    private String manufacturer;
    private BigDecimal price;
    private String description;
    private LocalDateTime createdAt;
    private boolean prescriptionRequired;

    private Medicine(){
        super();
    }

    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isPrescriptionRequired() {
        return prescriptionRequired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Medicine medicine = (Medicine) o;

        if (prescriptionRequired != medicine.prescriptionRequired) return false;
        if (!Objects.equals(name, medicine.name)) return false;
        if (!Objects.equals(manufacturer, medicine.manufacturer))
            return false;
        if (!Objects.equals(price, medicine.price)) return false;
        if (!Objects.equals(description, medicine.description))
            return false;
        return Objects.equals(createdAt, medicine.createdAt);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (manufacturer != null ? manufacturer.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (prescriptionRequired ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Medicine{");
        sb.append("name='").append(name).append('\'');
        sb.append(", manufacturer='").append(manufacturer).append('\'');
        sb.append(", price=").append(price);
        sb.append(", description='").append(description).append('\'');
        sb.append(", createdAt=").append(createdAt);
        sb.append(", prescriptionRequired=").append(prescriptionRequired);
        sb.append('}');
        return sb.toString();
    }

    public static Builder newBuilder() {
        return new Medicine().new Builder();
    }
    public class Builder {

        private Builder() {
        }

        public Builder setId(int id) {
            Medicine.super.setId(id);
            return this;
        }

        public Builder setName(String name) {
            Medicine.this.name = name;
            return this;
        }

        public Builder setManufacturer(String manufacturer) {
            Medicine.this.manufacturer = manufacturer;
            return this;
        }

        public Builder setDescription(String description) {
            Medicine.this.description = description;
            return this;
        }

        public Builder setPrescriptionRequired(boolean prescriptionRequired) {
            Medicine.this.prescriptionRequired = prescriptionRequired;
            return this;
        }

        public Builder setCreatedAt(LocalDateTime createdAt) {
            Medicine.this.createdAt = createdAt;
            return this;
        }

        public Builder setPrice(BigDecimal price) {
            Medicine.this.price = price;
            return this;
        }

        public Medicine build() {
            return Medicine.this;
        }

    }
}
