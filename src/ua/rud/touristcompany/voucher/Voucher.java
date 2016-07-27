package ua.rud.touristcompany.voucher;

public class Voucher {
    private String id;
    private Type type;
    private String country;
    private int days;
    private Transport transport;
    private int stars;
    private Meals meals;
    private int numberOfPersons;
    private boolean airCondition;
    private boolean tv;
    private int cost;
    private boolean transferIncluded;

    /*Getters and setters*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public Meals getMeals() {
        return meals;
    }

    public void setMeals(Meals meals) {
        this.meals = meals;
    }

    public int getNumberOfPersons() {
        return numberOfPersons;
    }

    public void setNumberOfPersons(int numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    public boolean isAirCondition() {
        return airCondition;
    }

    public void setAirCondition(boolean airCondition) {
        this.airCondition = airCondition;
    }

    public boolean isTv() {
        return tv;
    }

    public void setTv(boolean tv) {
        this.tv = tv;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public boolean isTransferIncluded() {
        return transferIncluded;
    }

    public void setTransferIncluded(boolean transferIncluded) {
        this.transferIncluded = transferIncluded;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Voucher{");
        sb.append("id='").append(id).append('\'');
        sb.append(", type=").append(type);
        sb.append(", country='").append(country).append('\'');
        sb.append(", days=").append(days);
        sb.append(", transport=").append(transport);
        sb.append(", stars=").append(stars);
        sb.append(", meals=").append(meals);
        sb.append(", numberOfPersons=").append(numberOfPersons);
        sb.append(", airCondition=").append(airCondition);
        sb.append(", tv=").append(tv);
        sb.append(", cost=").append(cost);
        sb.append(", transferIncluded=").append(transferIncluded);
        sb.append('}');
        return sb.toString();
    }
}
