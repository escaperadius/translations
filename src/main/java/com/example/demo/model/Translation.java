package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Translation {
  private String sourceLanguageId;
  private String targetLanguageId;
  private String model;
  private String submissionType;
  private String inputFormat;
  private String[] input;
  private String[] translation;
  private String[] dictionaries;
  private String translationMode;
  private String linguisticOptions;
  private int qualityEstimation;
  private String requestId;
}
