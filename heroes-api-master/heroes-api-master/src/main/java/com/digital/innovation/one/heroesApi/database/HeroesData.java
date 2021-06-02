package com.digital.innovation.one.heroesApi.database;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;

import static com.digital.innovation.one.heroesApi.constants.HeroesConstants.ENDPOINT_DYNAMO;
import static com.digital.innovation.one.heroesApi.constants.HeroesConstants.REGION_DYNAMO;


public class HeroesData {
    public static void main(String[] args) throws Exception {

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(ENDPOINT_DYNAMO, REGION_DYNAMO))
                .build();
        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable("Heroes_Table");

        Item hero = new Item()
                .withPrimaryKey("id","1")
                .withString("name", "Tempestade")
                .withString("universe", "Ultimate")
                .withString("place_of_origin", "Morocco")
                .withBoolean("identity_is_secret", false);

        Item hero2 = new Item()
                .withPrimaryKey("id", "2")
                .withString("name", "Vampira")
                .withString("universe", "Marvel")
                .withString("place_of_origin", "Caldecott County, Mississippi")
                .withBoolean("identity_is_secret", true);

        Item hero3 = new Item()
                .withPrimaryKey("id", "3")
                .withString("name", "Capitã marvel")
                .withString("universe", "House of M")
                .withString("place_of_origin", "Boston, Massachusetts")
                .withBoolean("identity_is_secret", false);

        PutItemOutcome outcome1 = table.putItem(hero);
        PutItemOutcome outcome2 = table.putItem(hero2);
        PutItemOutcome outcome3 = table.putItem(hero3);
        System.out.println("A tabela está preenchida com os exemplos :) ");

    }
}
