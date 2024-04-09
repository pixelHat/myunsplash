package com.myunsplash.backend;

import java.util.*;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.*;

@Repository
public class PhotosRepository {

  @Autowired
  private DynamoDBMapper dynamoDBmapper;

  public List<Photo> getImages(String id) {
    DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
      .withLimit(10);

    if (id.length() > 0) {
      Map<String, AttributeValue> exclusiveStartKey = new HashMap<>();
      exclusiveStartKey.put("id", new AttributeValue().withS(id));
      scanExpression.setExclusiveStartKey(exclusiveStartKey);
    }

    List<Photo> photos = new ArrayList<>();
    int count = 0;
    for (Photo photo : dynamoDBmapper.scan(Photo.class, scanExpression)) {
        photos.add(photo);
        count++;
        if (count >= 10) {
            break;
        }
    }
    return photos;
  }

  public Photo save(Photo photo) {
    dynamoDBmapper.save(photo);
    return photo;
  }

  public Photo delete(String id) {
    var photo = dynamoDBmapper.load(Photo.class, id);
    if (photo == null) {
      return null;
    }
    dynamoDBmapper.delete(photo);
    return photo;
  }

  public List<Photo> searchImages(String q) {
    DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
      .withLimit(10)
      .withFilterExpression("contains(label, :val)")
      .withExpressionAttributeValues(Map.of(":val", new AttributeValue().withS(q)));
    return dynamoDBmapper.scan(Photo.class, scanExpression);
  }
}
