package com.example.fonteasy;

import com.example.fonteasy.Classifier.Recognition;

import java.util.List;

public interface ResultsView {
  public void setResults(final List<Recognition> results);
}
