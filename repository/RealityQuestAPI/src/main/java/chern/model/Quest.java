package chern.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public class Quest {
   private long id;
   private String name;
   private String description;
   private String genre;
   private double price;
   private LocalTime duration;
   private LocalDate creationDate;
   private LocalDate modificationDate;
   private int maxPeople;

   private List<Tag> tags;

   public Quest() {
   }

   @Override
   public String toString() {
      return "Quest{" +
              "id=" + id +
              ", name='" + name + '\'' +
              ", description='" + description + '\'' +
              ", genre='" + genre + '\'' +
              ", price='" + price + '\'' +
              ", duration=" + duration +
              ", creationDate=" + creationDate +
              ", modificationDate=" + modificationDate +
              ", maxPeople=" + maxPeople +
              ", tags=" + tags +
              '}';
   }

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getGenre() {
      return genre;
   }

   public void setGenre(String genre) {
      this.genre = genre;
   }

   public double getPrice() {
      return price;
   }

   public void setPrice(double price) {
      this.price = price;
   }

   public LocalTime getDuration() {
      return duration;
   }

   public void setDuration(LocalTime duration) {
      this.duration = duration;
   }

   public LocalDate getCreationDate() {
      return creationDate;
   }

   public void setCreationDate(LocalDate creationDate) {
      this.creationDate = creationDate;
   }

   public LocalDate getModificationDate() {
      return modificationDate;
   }

   public void setModificationDate(LocalDate modificationDate) {
      this.modificationDate = modificationDate;
   }

   public int getMaxPeople() {
      return maxPeople;
   }

   public void setMaxPeople(int maxPeople) {
      this.maxPeople = maxPeople;
   }

   public List<Tag> getTags() {
      return tags;
   }

   public void setTags(List<Tag> tags) {
      this.tags = tags;
   }
}
