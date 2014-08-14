import java.io.*;
import java.util.*;
import java.util.StringTokenizer;

public class PredictWithID3_500trained
{
	public static final String PATH_TO_TEST_FILE = "adult.data.test.data";
	public static final String PATH_TO_COMPARE_FILE = "adult.data.compare.data";

	public static int total_count = 0;
	public static int predict_count = 0;
	public static int fail_count = 0;

	public static void main(String[] args) throws Exception {
		readData();
		System.out.println("Total lines read: " + total_count);
		System.out.println("Total lines successfully predicted: " + predict_count);
		System.out.println("Total lines failed prediction: " + fail_count);
		return;
	}
	
	public static void readData() throws Exception {
		String income = "";

		BufferedReader reader = null;
		BufferedReader reader2 = null;
		FileInputStream fis = null;
		FileInputStream fis2 = null;

		boolean bFirstRead = true;

        try { 
           File f = new File(PATH_TO_TEST_FILE);
           fis = new FileInputStream(f); 
           reader = new BufferedReader(new InputStreamReader(fis));;
           
           File f2 = new File(PATH_TO_COMPARE_FILE);
           fis2 = new FileInputStream(f2); 
           reader2 = new BufferedReader(new InputStreamReader(fis2));;
           
           // read the first record of the file
           String line;
           String line2;
           while ((line = reader.readLine()) != null) {
			  if ((line2 = reader2.readLine()) == null) { throw new Exception("Row mismatch!"); }
              StringTokenizer st = new StringTokenizer(line, " ");
              income = line2.trim();
			  
			  int tokenCount = st.countTokens();
			  if (bFirstRead) {
				//System.out.println(line);
			    if (!income.equalsIgnoreCase("income_class")) { throw new Exception("Bad compare file header!"); }
				if (!st.nextToken().equalsIgnoreCase("age_class")) { throw new Exception("Bad test file header: age_class!"); }
				if (!st.nextToken().equalsIgnoreCase("workclass")) { throw new Exception("Bad test file header: workclass!"); }
				if (!st.nextToken().equalsIgnoreCase("education")) { throw new Exception("Bad test file header: education!"); }
				if (!st.nextToken().equalsIgnoreCase("marital_status")) { throw new Exception("Bad test file header: marital_status!"); }
				//if (!st.nextToken().equalsIgnoreCase("occupation")) { throw new Exception("Bad test file header: occupation!"); }
				if (!st.nextToken().equalsIgnoreCase("relationship")) { throw new Exception("Bad test file header: relationship!"); }
				if (!st.nextToken().equalsIgnoreCase("race")) { throw new Exception("Bad test file header: race!"); }
				if (!st.nextToken().equalsIgnoreCase("sex")) { throw new Exception("Bad test file header: sex!"); }
				if (!st.nextToken().equalsIgnoreCase("hours_class")) { throw new Exception("Bad test file header: hours_class!"); }
				//if (st.nextToken() != "native_country") { throw new Exception("Bad test file header: native_country!"); }
				
				System.out.println("count: " + tokenCount + "; NUM_ATTRS:" + 8);
				if(tokenCount != 8) {
					throw new Exception("Unknown number of attributes!");
				}
				
				bFirstRead = false;
			  } 
			  else 
			  {
				  String age_class = st.nextToken();
				  String workclass = st.nextToken();
				  String education = st.nextToken();
				  String marital_status = st.nextToken();
				  //String occupation = st.nextToken();
				  String relationship = st.nextToken();
				  String race = st.nextToken();
				  String sex = st.nextToken();
				  String hours_class = st.nextToken();
				  //String native_country = st.nextToken();

				  total_count++;
				  String predictedIncome = predictIncomeClass(	age_class.trim(), 
																workclass.trim(), 
																education.trim(), 
																marital_status.trim(), 
																relationship.trim(), 
																race.trim(), 
																sex.trim(), 
																hours_class.trim());
				  System.out.println("file income: " + income + "; predicted income: " + predictedIncome);
				  if(income.equalsIgnoreCase(predictedIncome)) { predict_count++; } else { fail_count++; }
			  }
           }

        } 
        catch (IOException e) { 
           System.out.println("Uh oh, got an IOException error: " + e.getMessage()); 
        } 
        catch (Exception e) {
            System.out.println("Uh oh, got an Exception error: " + e.getMessage()); 
        }
        finally { 
           if (fis != null) {
              try {
                 fis.close();
              } catch (IOException ioe) {
                 System.out.println("IOException error trying to close the file: " + ioe.getMessage()); 
              }
           }
           if (fis2 != null) {
              try {
                 fis2.close();
              } catch (IOException ioe) {
                 System.out.println("IOException error trying to close the file: " + ioe.getMessage()); 
              }
           }
        }
		return;
	}

	public static String predictIncomeClass( String age_class, 
											 String workclass, 
											 String education, 
											 String marital_status, 
											 String relationship, 
											 String race, 
											 String sex, 
											 String hours_class) 
	{
		String income_class = "";

		if( relationship.equalsIgnoreCase("Not-in-family")) {
			if( education.equalsIgnoreCase("Bachelors")) {
				if( race.equalsIgnoreCase("White")) {
					if( marital_status.equalsIgnoreCase("Never-married")) {
							income_class = "<=50K";
					} else 			if( marital_status.equalsIgnoreCase("Married-civ-spouse")) {
							//
					} else 			if( marital_status.equalsIgnoreCase("Divorced")) {
						if( workclass.equalsIgnoreCase("State-gov")) {
								//
						} else 				if( workclass.equalsIgnoreCase("Self-emp-not-inc")) {
								income_class = "<=50K";
						} else 				if( workclass.equalsIgnoreCase("Private")) {
							if( age_class.equalsIgnoreCase("U40")) {
									//
							} else 					if( age_class.equalsIgnoreCase("U55")) {
									income_class = ">50K";
							} else 					if( age_class.equalsIgnoreCase("U30")) {
									//
							} else 					if( age_class.equalsIgnoreCase("55PLUS")) {
								if( sex.equalsIgnoreCase("Male")) {
										//
								} else 						if( sex.equalsIgnoreCase("Female")) {
									if( hours_class.equalsIgnoreCase("UPTO40")) {
											//
									} else 							if( hours_class.equalsIgnoreCase("UPTO20")) {
											//
									} else 							if( hours_class.equalsIgnoreCase("UPTO60")) {
											//
									} else 							if( hours_class.equalsIgnoreCase("UPTO80")) {
											//
									} else 							if( hours_class.equalsIgnoreCase("80PLUS")) {
											//
									}
								}
							} else 					if( age_class.equalsIgnoreCase("U21")) {
									//
							}
						} else 				if( workclass.equalsIgnoreCase("Federal-gov")) {
								income_class = ">50K";
						} else 				if( workclass.equalsIgnoreCase("Local-gov")) {
								//
						} else 				if( workclass.equalsIgnoreCase("?")) {
								income_class = "<=50K";
						} else 				if( workclass.equalsIgnoreCase("Self-emp-inc")) {
								income_class = "<=50K";
						}
					} else 			if( marital_status.equalsIgnoreCase("Married-spouse-absent")) {
							income_class = "<=50K";
					} else 			if( marital_status.equalsIgnoreCase("Separated")) {
							//
					} else 			if( marital_status.equalsIgnoreCase("Married-AF-spouse")) {
							//
					} else 			if( marital_status.equalsIgnoreCase("Widowed")) {
							//
					}
				} else 		if( race.equalsIgnoreCase("Black")) {
						income_class = ">50K";
				} else 		if( race.equalsIgnoreCase("Asian-Pac-Islander")) {
						//
				} else 		if( race.equalsIgnoreCase("Amer-Indian-Eskimo")) {
						//
				} else 		if( race.equalsIgnoreCase("Other")) {
						//
				}
			} else 	if( education.equalsIgnoreCase("HS-grad")) {
				if( age_class.equalsIgnoreCase("U40")) {
						income_class = "<=50K";
				} else 		if( age_class.equalsIgnoreCase("U55")) {
					if( workclass.equalsIgnoreCase("State-gov")) {
							//
					} else 			if( workclass.equalsIgnoreCase("Self-emp-not-inc")) {
							//
					} else 			if( workclass.equalsIgnoreCase("Private")) {
						if( marital_status.equalsIgnoreCase("Never-married")) {
								income_class = "<=50K";
						} else 				if( marital_status.equalsIgnoreCase("Married-civ-spouse")) {
								//
						} else 				if( marital_status.equalsIgnoreCase("Divorced")) {
							if( sex.equalsIgnoreCase("Male")) {
									income_class = "<=50K";
							} else 					if( sex.equalsIgnoreCase("Female")) {
								if( race.equalsIgnoreCase("White")) {
									if( hours_class.equalsIgnoreCase("UPTO40")) {
											//
									} else 							if( hours_class.equalsIgnoreCase("UPTO20")) {
											//
									} else 							if( hours_class.equalsIgnoreCase("UPTO60")) {
											//
									} else 							if( hours_class.equalsIgnoreCase("UPTO80")) {
											//
									} else 							if( hours_class.equalsIgnoreCase("80PLUS")) {
											//
									}
								} else 						if( race.equalsIgnoreCase("Black")) {
										//
								} else 						if( race.equalsIgnoreCase("Asian-Pac-Islander")) {
										//
								} else 						if( race.equalsIgnoreCase("Amer-Indian-Eskimo")) {
										//
								} else 						if( race.equalsIgnoreCase("Other")) {
										//
								}
							}
						} else 				if( marital_status.equalsIgnoreCase("Married-spouse-absent")) {
								//
						} else 				if( marital_status.equalsIgnoreCase("Separated")) {
								//
						} else 				if( marital_status.equalsIgnoreCase("Married-AF-spouse")) {
								//
						} else 				if( marital_status.equalsIgnoreCase("Widowed")) {
								//
						}
					} else 			if( workclass.equalsIgnoreCase("Federal-gov")) {
							income_class = "<=50K";
					} else 			if( workclass.equalsIgnoreCase("Local-gov")) {
							//
					} else 			if( workclass.equalsIgnoreCase("?")) {
							income_class = ">50K";
					} else 			if( workclass.equalsIgnoreCase("Self-emp-inc")) {
							income_class = "<=50K";
					}
				} else 		if( age_class.equalsIgnoreCase("U30")) {
						income_class = "<=50K";
				} else 		if( age_class.equalsIgnoreCase("55PLUS")) {
						income_class = "<=50K";
				} else 		if( age_class.equalsIgnoreCase("U21")) {
						income_class = "<=50K";
				}
			} else 	if( education.equalsIgnoreCase("11th")) {
					income_class = "<=50K";
			} else 	if( education.equalsIgnoreCase("Masters")) {
				if( sex.equalsIgnoreCase("Male")) {
						income_class = "<=50K";
				} else 		if( sex.equalsIgnoreCase("Female")) {
					if( age_class.equalsIgnoreCase("U40")) {
							income_class = ">50K";
					} else 			if( age_class.equalsIgnoreCase("U55")) {
						if( workclass.equalsIgnoreCase("State-gov")) {
								//
						} else 				if( workclass.equalsIgnoreCase("Self-emp-not-inc")) {
								income_class = "<=50K";
						} else 				if( workclass.equalsIgnoreCase("Private")) {
								//
						} else 				if( workclass.equalsIgnoreCase("Federal-gov")) {
								income_class = ">50K";
						} else 				if( workclass.equalsIgnoreCase("Local-gov")) {
								//
						} else 				if( workclass.equalsIgnoreCase("?")) {
								//
						} else 				if( workclass.equalsIgnoreCase("Self-emp-inc")) {
								//
						}
					} else 			if( age_class.equalsIgnoreCase("U30")) {
							income_class = "<=50K";
					} else 			if( age_class.equalsIgnoreCase("55PLUS")) {
							//
					} else 			if( age_class.equalsIgnoreCase("U21")) {
							//
					}
				}
			} else 	if( education.equalsIgnoreCase("9th")) {
					income_class = "<=50K";
			} else 	if( education.equalsIgnoreCase("Some-college")) {
					income_class = "<=50K";
			} else 	if( education.equalsIgnoreCase("Assoc-acdm")) {
					income_class = "<=50K";
			} else 	if( education.equalsIgnoreCase("Assoc-voc")) {
				if( age_class.equalsIgnoreCase("U40")) {
						income_class = "<=50K";
				} else 		if( age_class.equalsIgnoreCase("U55")) {
					if( marital_status.equalsIgnoreCase("Never-married")) {
						if( sex.equalsIgnoreCase("Male")) {
								income_class = "<=50K";
						} else 				if( sex.equalsIgnoreCase("Female")) {
								income_class = ">50K";
						}
					} else 			if( marital_status.equalsIgnoreCase("Married-civ-spouse")) {
							//
					} else 			if( marital_status.equalsIgnoreCase("Divorced")) {
							//
					} else 			if( marital_status.equalsIgnoreCase("Married-spouse-absent")) {
							//
					} else 			if( marital_status.equalsIgnoreCase("Separated")) {
							//
					} else 			if( marital_status.equalsIgnoreCase("Married-AF-spouse")) {
							//
					} else 			if( marital_status.equalsIgnoreCase("Widowed")) {
							income_class = "<=50K";
					}
				} else 		if( age_class.equalsIgnoreCase("U30")) {
						income_class = "<=50K";
				} else 		if( age_class.equalsIgnoreCase("55PLUS")) {
						income_class = "<=50K";
				} else 		if( age_class.equalsIgnoreCase("U21")) {
						//
				}
			} else 	if( education.equalsIgnoreCase("7th-8th")) {
					income_class = "<=50K";
			} else 	if( education.equalsIgnoreCase("Doctorate")) {
					income_class = ">50K";
			} else 	if( education.equalsIgnoreCase("Prof-school")) {
					income_class = ">50K";
			} else 	if( education.equalsIgnoreCase("5th-6th")) {
					income_class = "<=50K";
			} else 	if( education.equalsIgnoreCase("10th")) {
					income_class = "<=50K";
			} else 	if( education.equalsIgnoreCase("1st-4th")) {
					income_class = "<=50K";
			} else 	if( education.equalsIgnoreCase("Preschool")) {
					income_class = "<=50K";
			} else 	if( education.equalsIgnoreCase("12th")) {
					income_class = ">50K";
			}
		} else if( relationship.equalsIgnoreCase("Husband")) {
			if( education.equalsIgnoreCase("Bachelors")) {
				if( workclass.equalsIgnoreCase("State-gov")) {
						income_class = ">50K";
				} else 		if( workclass.equalsIgnoreCase("Self-emp-not-inc")) {
					if( age_class.equalsIgnoreCase("U40")) {
							//
					} else 			if( age_class.equalsIgnoreCase("U55")) {
							income_class = "<=50K";
					} else 			if( age_class.equalsIgnoreCase("U30")) {
							income_class = ">50K";
					} else 			if( age_class.equalsIgnoreCase("55PLUS")) {
							//
					} else 			if( age_class.equalsIgnoreCase("U21")) {
							//
					}
				} else 		if( workclass.equalsIgnoreCase("Private")) {
					if( hours_class.equalsIgnoreCase("UPTO40")) {
						if( race.equalsIgnoreCase("White")) {
							if( age_class.equalsIgnoreCase("U40")) {
								if( marital_status.equalsIgnoreCase("Never-married")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Married-civ-spouse")) {
									if( sex.equalsIgnoreCase("Male")) {
											//
									} else 							if( sex.equalsIgnoreCase("Female")) {
											//
									}
								} else 						if( marital_status.equalsIgnoreCase("Divorced")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Married-spouse-absent")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Separated")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Married-AF-spouse")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Widowed")) {
										//
								}
							} else 					if( age_class.equalsIgnoreCase("U55")) {
								if( marital_status.equalsIgnoreCase("Never-married")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Married-civ-spouse")) {
									if( sex.equalsIgnoreCase("Male")) {
											//
									} else 							if( sex.equalsIgnoreCase("Female")) {
											//
									}
								} else 						if( marital_status.equalsIgnoreCase("Divorced")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Married-spouse-absent")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Separated")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Married-AF-spouse")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Widowed")) {
										//
								}
							} else 					if( age_class.equalsIgnoreCase("U30")) {
									//
							} else 					if( age_class.equalsIgnoreCase("55PLUS")) {
									//
							} else 					if( age_class.equalsIgnoreCase("U21")) {
									//
							}
						} else 				if( race.equalsIgnoreCase("Black")) {
								income_class = ">50K";
						} else 				if( race.equalsIgnoreCase("Asian-Pac-Islander")) {
								//
						} else 				if( race.equalsIgnoreCase("Amer-Indian-Eskimo")) {
								//
						} else 				if( race.equalsIgnoreCase("Other")) {
								//
						}
					} else 			if( hours_class.equalsIgnoreCase("UPTO20")) {
							//
					} else 			if( hours_class.equalsIgnoreCase("UPTO60")) {
						if( age_class.equalsIgnoreCase("U40")) {
							if( marital_status.equalsIgnoreCase("Never-married")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Married-civ-spouse")) {
								if( race.equalsIgnoreCase("White")) {
									if( sex.equalsIgnoreCase("Male")) {
											//
									} else 							if( sex.equalsIgnoreCase("Female")) {
											//
									}
								} else 						if( race.equalsIgnoreCase("Black")) {
										//
								} else 						if( race.equalsIgnoreCase("Asian-Pac-Islander")) {
										//
								} else 						if( race.equalsIgnoreCase("Amer-Indian-Eskimo")) {
										//
								} else 						if( race.equalsIgnoreCase("Other")) {
										//
								}
							} else 					if( marital_status.equalsIgnoreCase("Divorced")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Married-spouse-absent")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Separated")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Married-AF-spouse")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Widowed")) {
									//
							}
						} else 				if( age_class.equalsIgnoreCase("U55")) {
							if( marital_status.equalsIgnoreCase("Never-married")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Married-civ-spouse")) {
								if( race.equalsIgnoreCase("White")) {
									if( sex.equalsIgnoreCase("Male")) {
											//
									} else 							if( sex.equalsIgnoreCase("Female")) {
											//
									}
								} else 						if( race.equalsIgnoreCase("Black")) {
										//
								} else 						if( race.equalsIgnoreCase("Asian-Pac-Islander")) {
										//
								} else 						if( race.equalsIgnoreCase("Amer-Indian-Eskimo")) {
										//
								} else 						if( race.equalsIgnoreCase("Other")) {
										//
								}
							} else 					if( marital_status.equalsIgnoreCase("Divorced")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Married-spouse-absent")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Separated")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Married-AF-spouse")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Widowed")) {
									//
							}
						} else 				if( age_class.equalsIgnoreCase("U30")) {
							if( marital_status.equalsIgnoreCase("Never-married")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Married-civ-spouse")) {
								if( race.equalsIgnoreCase("White")) {
									if( sex.equalsIgnoreCase("Male")) {
											//
									} else 							if( sex.equalsIgnoreCase("Female")) {
											//
									}
								} else 						if( race.equalsIgnoreCase("Black")) {
										//
								} else 						if( race.equalsIgnoreCase("Asian-Pac-Islander")) {
										//
								} else 						if( race.equalsIgnoreCase("Amer-Indian-Eskimo")) {
										//
								} else 						if( race.equalsIgnoreCase("Other")) {
										//
								}
							} else 					if( marital_status.equalsIgnoreCase("Divorced")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Married-spouse-absent")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Separated")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Married-AF-spouse")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Widowed")) {
									//
							}
						} else 				if( age_class.equalsIgnoreCase("55PLUS")) {
								//
						} else 				if( age_class.equalsIgnoreCase("U21")) {
								//
						}
					} else 			if( hours_class.equalsIgnoreCase("UPTO80")) {
							income_class = "<=50K";
					} else 			if( hours_class.equalsIgnoreCase("80PLUS")) {
							//
					}
				} else 		if( workclass.equalsIgnoreCase("Federal-gov")) {
					if( age_class.equalsIgnoreCase("U40")) {
							//
					} else 			if( age_class.equalsIgnoreCase("U55")) {
							income_class = "<=50K";
					} else 			if( age_class.equalsIgnoreCase("U30")) {
							//
					} else 			if( age_class.equalsIgnoreCase("55PLUS")) {
							income_class = ">50K";
					} else 			if( age_class.equalsIgnoreCase("U21")) {
							//
					}
				} else 		if( workclass.equalsIgnoreCase("Local-gov")) {
					if( age_class.equalsIgnoreCase("U40")) {
							income_class = ">50K";
					} else 			if( age_class.equalsIgnoreCase("U55")) {
							income_class = "<=50K";
					} else 			if( age_class.equalsIgnoreCase("U30")) {
							//
					} else 			if( age_class.equalsIgnoreCase("55PLUS")) {
							income_class = ">50K";
					} else 			if( age_class.equalsIgnoreCase("U21")) {
							//
					}
				} else 		if( workclass.equalsIgnoreCase("?")) {
						income_class = ">50K";
				} else 		if( workclass.equalsIgnoreCase("Self-emp-inc")) {
						income_class = ">50K";
				}
			} else 	if( education.equalsIgnoreCase("HS-grad")) {
				if( age_class.equalsIgnoreCase("U40")) {
					if( workclass.equalsIgnoreCase("State-gov")) {
							income_class = ">50K";
					} else 			if( workclass.equalsIgnoreCase("Self-emp-not-inc")) {
							income_class = "<=50K";
					} else 			if( workclass.equalsIgnoreCase("Private")) {
						if( race.equalsIgnoreCase("White")) {
							if( hours_class.equalsIgnoreCase("UPTO40")) {
								if( marital_status.equalsIgnoreCase("Never-married")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Married-civ-spouse")) {
									if( sex.equalsIgnoreCase("Male")) {
											//
									} else 							if( sex.equalsIgnoreCase("Female")) {
											//
									}
								} else 						if( marital_status.equalsIgnoreCase("Divorced")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Married-spouse-absent")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Separated")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Married-AF-spouse")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Widowed")) {
										//
								}
							} else 					if( hours_class.equalsIgnoreCase("UPTO20")) {
									//
							} else 					if( hours_class.equalsIgnoreCase("UPTO60")) {
								if( marital_status.equalsIgnoreCase("Never-married")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Married-civ-spouse")) {
									if( sex.equalsIgnoreCase("Male")) {
											//
									} else 							if( sex.equalsIgnoreCase("Female")) {
											//
									}
								} else 						if( marital_status.equalsIgnoreCase("Divorced")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Married-spouse-absent")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Separated")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Married-AF-spouse")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Widowed")) {
										//
								}
							} else 					if( hours_class.equalsIgnoreCase("UPTO80")) {
									//
							} else 					if( hours_class.equalsIgnoreCase("80PLUS")) {
									//
							}
						} else 				if( race.equalsIgnoreCase("Black")) {
								income_class = "<=50K";
						} else 				if( race.equalsIgnoreCase("Asian-Pac-Islander")) {
								//
						} else 				if( race.equalsIgnoreCase("Amer-Indian-Eskimo")) {
								//
						} else 				if( race.equalsIgnoreCase("Other")) {
								//
						}
					} else 			if( workclass.equalsIgnoreCase("Federal-gov")) {
							income_class = ">50K";
					} else 			if( workclass.equalsIgnoreCase("Local-gov")) {
							//
					} else 			if( workclass.equalsIgnoreCase("?")) {
							income_class = "<=50K";
					} else 			if( workclass.equalsIgnoreCase("Self-emp-inc")) {
							income_class = ">50K";
					}
				} else 		if( age_class.equalsIgnoreCase("U55")) {
					if( workclass.equalsIgnoreCase("State-gov")) {
							//
					} else 			if( workclass.equalsIgnoreCase("Self-emp-not-inc")) {
						if( race.equalsIgnoreCase("White")) {
							if( hours_class.equalsIgnoreCase("UPTO40")) {
									income_class = "<=50K";
							} else 					if( hours_class.equalsIgnoreCase("UPTO20")) {
									//
							} else 					if( hours_class.equalsIgnoreCase("UPTO60")) {
								if( marital_status.equalsIgnoreCase("Never-married")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Married-civ-spouse")) {
									if( sex.equalsIgnoreCase("Male")) {
											//
									} else 							if( sex.equalsIgnoreCase("Female")) {
											//
									}
								} else 						if( marital_status.equalsIgnoreCase("Divorced")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Married-spouse-absent")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Separated")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Married-AF-spouse")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Widowed")) {
										//
								}
							} else 					if( hours_class.equalsIgnoreCase("UPTO80")) {
									//
							} else 					if( hours_class.equalsIgnoreCase("80PLUS")) {
									//
							}
						} else 				if( race.equalsIgnoreCase("Black")) {
								//
						} else 				if( race.equalsIgnoreCase("Asian-Pac-Islander")) {
								income_class = ">50K";
						} else 				if( race.equalsIgnoreCase("Amer-Indian-Eskimo")) {
								//
						} else 				if( race.equalsIgnoreCase("Other")) {
								//
						}
					} else 			if( workclass.equalsIgnoreCase("Private")) {
							income_class = "<=50K";
					} else 			if( workclass.equalsIgnoreCase("Federal-gov")) {
							//
					} else 			if( workclass.equalsIgnoreCase("Local-gov")) {
						if( race.equalsIgnoreCase("White")) {
								income_class = ">50K";
						} else 				if( race.equalsIgnoreCase("Black")) {
								income_class = "<=50K";
						} else 				if( race.equalsIgnoreCase("Asian-Pac-Islander")) {
								//
						} else 				if( race.equalsIgnoreCase("Amer-Indian-Eskimo")) {
								//
						} else 				if( race.equalsIgnoreCase("Other")) {
								//
						}
					} else 			if( workclass.equalsIgnoreCase("?")) {
							//
					} else 			if( workclass.equalsIgnoreCase("Self-emp-inc")) {
							//
					}
				} else 		if( age_class.equalsIgnoreCase("U30")) {
						income_class = "<=50K";
				} else 		if( age_class.equalsIgnoreCase("55PLUS")) {
					if( workclass.equalsIgnoreCase("State-gov")) {
							income_class = "<=50K";
					} else 			if( workclass.equalsIgnoreCase("Self-emp-not-inc")) {
						if( hours_class.equalsIgnoreCase("UPTO40")) {
								income_class = "<=50K";
						} else 				if( hours_class.equalsIgnoreCase("UPTO20")) {
								//
						} else 				if( hours_class.equalsIgnoreCase("UPTO60")) {
							if( marital_status.equalsIgnoreCase("Never-married")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Married-civ-spouse")) {
								if( race.equalsIgnoreCase("White")) {
									if( sex.equalsIgnoreCase("Male")) {
											//
									} else 							if( sex.equalsIgnoreCase("Female")) {
											//
									}
								} else 						if( race.equalsIgnoreCase("Black")) {
										//
								} else 						if( race.equalsIgnoreCase("Asian-Pac-Islander")) {
										//
								} else 						if( race.equalsIgnoreCase("Amer-Indian-Eskimo")) {
										//
								} else 						if( race.equalsIgnoreCase("Other")) {
										//
								}
							} else 					if( marital_status.equalsIgnoreCase("Divorced")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Married-spouse-absent")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Separated")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Married-AF-spouse")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Widowed")) {
									//
							}
						} else 				if( hours_class.equalsIgnoreCase("UPTO80")) {
								//
						} else 				if( hours_class.equalsIgnoreCase("80PLUS")) {
								//
						}
					} else 			if( workclass.equalsIgnoreCase("Private")) {
						if( hours_class.equalsIgnoreCase("UPTO40")) {
							if( race.equalsIgnoreCase("White")) {
								if( marital_status.equalsIgnoreCase("Never-married")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Married-civ-spouse")) {
									if( sex.equalsIgnoreCase("Male")) {
											//
									} else 							if( sex.equalsIgnoreCase("Female")) {
											//
									}
								} else 						if( marital_status.equalsIgnoreCase("Divorced")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Married-spouse-absent")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Separated")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Married-AF-spouse")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Widowed")) {
										//
								}
							} else 					if( race.equalsIgnoreCase("Black")) {
									//
							} else 					if( race.equalsIgnoreCase("Asian-Pac-Islander")) {
									income_class = "<=50K";
							} else 					if( race.equalsIgnoreCase("Amer-Indian-Eskimo")) {
									//
							} else 					if( race.equalsIgnoreCase("Other")) {
									//
							}
						} else 				if( hours_class.equalsIgnoreCase("UPTO20")) {
								income_class = "<=50K";
						} else 				if( hours_class.equalsIgnoreCase("UPTO60")) {
							if( marital_status.equalsIgnoreCase("Never-married")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Married-civ-spouse")) {
								if( race.equalsIgnoreCase("White")) {
									if( sex.equalsIgnoreCase("Male")) {
											//
									} else 							if( sex.equalsIgnoreCase("Female")) {
											//
									}
								} else 						if( race.equalsIgnoreCase("Black")) {
										//
								} else 						if( race.equalsIgnoreCase("Asian-Pac-Islander")) {
										//
								} else 						if( race.equalsIgnoreCase("Amer-Indian-Eskimo")) {
										//
								} else 						if( race.equalsIgnoreCase("Other")) {
										//
								}
							} else 					if( marital_status.equalsIgnoreCase("Divorced")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Married-spouse-absent")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Separated")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Married-AF-spouse")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Widowed")) {
									//
							}
						} else 				if( hours_class.equalsIgnoreCase("UPTO80")) {
								//
						} else 				if( hours_class.equalsIgnoreCase("80PLUS")) {
								//
						}
					} else 			if( workclass.equalsIgnoreCase("Federal-gov")) {
							//
					} else 			if( workclass.equalsIgnoreCase("Local-gov")) {
							income_class = "<=50K";
					} else 			if( workclass.equalsIgnoreCase("?")) {
							//
					} else 			if( workclass.equalsIgnoreCase("Self-emp-inc")) {
						if( marital_status.equalsIgnoreCase("Never-married")) {
								//
						} else 				if( marital_status.equalsIgnoreCase("Married-civ-spouse")) {
							if( race.equalsIgnoreCase("White")) {
								if( sex.equalsIgnoreCase("Male")) {
									if( hours_class.equalsIgnoreCase("UPTO40")) {
											//
									} else 							if( hours_class.equalsIgnoreCase("UPTO20")) {
											//
									} else 							if( hours_class.equalsIgnoreCase("UPTO60")) {
											//
									} else 							if( hours_class.equalsIgnoreCase("UPTO80")) {
											//
									} else 							if( hours_class.equalsIgnoreCase("80PLUS")) {
											//
									}
								} else 						if( sex.equalsIgnoreCase("Female")) {
										//
								}
							} else 					if( race.equalsIgnoreCase("Black")) {
									//
							} else 					if( race.equalsIgnoreCase("Asian-Pac-Islander")) {
									//
							} else 					if( race.equalsIgnoreCase("Amer-Indian-Eskimo")) {
									//
							} else 					if( race.equalsIgnoreCase("Other")) {
									//
							}
						} else 				if( marital_status.equalsIgnoreCase("Divorced")) {
								//
						} else 				if( marital_status.equalsIgnoreCase("Married-spouse-absent")) {
								//
						} else 				if( marital_status.equalsIgnoreCase("Separated")) {
								//
						} else 				if( marital_status.equalsIgnoreCase("Married-AF-spouse")) {
								//
						} else 				if( marital_status.equalsIgnoreCase("Widowed")) {
								//
						}
					}
				} else 		if( age_class.equalsIgnoreCase("U21")) {
						income_class = "<=50K";
				}
			} else 	if( education.equalsIgnoreCase("11th")) {
					income_class = "<=50K";
			} else 	if( education.equalsIgnoreCase("Masters")) {
				if( hours_class.equalsIgnoreCase("UPTO40")) {
					if( workclass.equalsIgnoreCase("State-gov")) {
							//
					} else 			if( workclass.equalsIgnoreCase("Self-emp-not-inc")) {
							income_class = ">50K";
					} else 			if( workclass.equalsIgnoreCase("Private")) {
							income_class = ">50K";
					} else 			if( workclass.equalsIgnoreCase("Federal-gov")) {
							income_class = ">50K";
					} else 			if( workclass.equalsIgnoreCase("Local-gov")) {
							income_class = "<=50K";
					} else 			if( workclass.equalsIgnoreCase("?")) {
							//
					} else 			if( workclass.equalsIgnoreCase("Self-emp-inc")) {
							//
					}
				} else 		if( hours_class.equalsIgnoreCase("UPTO20")) {
						income_class = "<=50K";
				} else 		if( hours_class.equalsIgnoreCase("UPTO60")) {
					if( age_class.equalsIgnoreCase("U40")) {
						if( workclass.equalsIgnoreCase("State-gov")) {
								//
						} else 				if( workclass.equalsIgnoreCase("Self-emp-not-inc")) {
								//
						} else 				if( workclass.equalsIgnoreCase("Private")) {
							if( marital_status.equalsIgnoreCase("Never-married")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Married-civ-spouse")) {
								if( race.equalsIgnoreCase("White")) {
									if( sex.equalsIgnoreCase("Male")) {
											//
									} else 							if( sex.equalsIgnoreCase("Female")) {
											//
									}
								} else 						if( race.equalsIgnoreCase("Black")) {
										//
								} else 						if( race.equalsIgnoreCase("Asian-Pac-Islander")) {
										//
								} else 						if( race.equalsIgnoreCase("Amer-Indian-Eskimo")) {
										//
								} else 						if( race.equalsIgnoreCase("Other")) {
										//
								}
							} else 					if( marital_status.equalsIgnoreCase("Divorced")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Married-spouse-absent")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Separated")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Married-AF-spouse")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Widowed")) {
									//
							}
						} else 				if( workclass.equalsIgnoreCase("Federal-gov")) {
								//
						} else 				if( workclass.equalsIgnoreCase("Local-gov")) {
								//
						} else 				if( workclass.equalsIgnoreCase("?")) {
								//
						} else 				if( workclass.equalsIgnoreCase("Self-emp-inc")) {
								//
						}
					} else 			if( age_class.equalsIgnoreCase("U55")) {
							income_class = ">50K";
					} else 			if( age_class.equalsIgnoreCase("U30")) {
							//
					} else 			if( age_class.equalsIgnoreCase("55PLUS")) {
							//
					} else 			if( age_class.equalsIgnoreCase("U21")) {
							//
					}
				} else 		if( hours_class.equalsIgnoreCase("UPTO80")) {
						income_class = ">50K";
				} else 		if( hours_class.equalsIgnoreCase("80PLUS")) {
						income_class = "<=50K";
				}
			} else 	if( education.equalsIgnoreCase("9th")) {
					income_class = "<=50K";
			} else 	if( education.equalsIgnoreCase("Some-college")) {
				if( age_class.equalsIgnoreCase("U40")) {
					if( workclass.equalsIgnoreCase("State-gov")) {
							income_class = "<=50K";
					} else 			if( workclass.equalsIgnoreCase("Self-emp-not-inc")) {
							//
					} else 			if( workclass.equalsIgnoreCase("Private")) {
						if( hours_class.equalsIgnoreCase("UPTO40")) {
							if( marital_status.equalsIgnoreCase("Never-married")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Married-civ-spouse")) {
								if( race.equalsIgnoreCase("White")) {
									if( sex.equalsIgnoreCase("Male")) {
											//
									} else 							if( sex.equalsIgnoreCase("Female")) {
											//
									}
								} else 						if( race.equalsIgnoreCase("Black")) {
										//
								} else 						if( race.equalsIgnoreCase("Asian-Pac-Islander")) {
										//
								} else 						if( race.equalsIgnoreCase("Amer-Indian-Eskimo")) {
										//
								} else 						if( race.equalsIgnoreCase("Other")) {
										//
								}
							} else 					if( marital_status.equalsIgnoreCase("Divorced")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Married-spouse-absent")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Separated")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Married-AF-spouse")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Widowed")) {
									//
							}
						} else 				if( hours_class.equalsIgnoreCase("UPTO20")) {
								//
						} else 				if( hours_class.equalsIgnoreCase("UPTO60")) {
							if( race.equalsIgnoreCase("White")) {
								if( marital_status.equalsIgnoreCase("Never-married")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Married-civ-spouse")) {
									if( sex.equalsIgnoreCase("Male")) {
											//
									} else 							if( sex.equalsIgnoreCase("Female")) {
											//
									}
								} else 						if( marital_status.equalsIgnoreCase("Divorced")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Married-spouse-absent")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Separated")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Married-AF-spouse")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Widowed")) {
										//
								}
							} else 					if( race.equalsIgnoreCase("Black")) {
									income_class = "<=50K";
							} else 					if( race.equalsIgnoreCase("Asian-Pac-Islander")) {
									//
							} else 					if( race.equalsIgnoreCase("Amer-Indian-Eskimo")) {
									//
							} else 					if( race.equalsIgnoreCase("Other")) {
									//
							}
						} else 				if( hours_class.equalsIgnoreCase("UPTO80")) {
								income_class = ">50K";
						} else 				if( hours_class.equalsIgnoreCase("80PLUS")) {
								//
						}
					} else 			if( workclass.equalsIgnoreCase("Federal-gov")) {
							income_class = ">50K";
					} else 			if( workclass.equalsIgnoreCase("Local-gov")) {
							//
					} else 			if( workclass.equalsIgnoreCase("?")) {
							income_class = "<=50K";
					} else 			if( workclass.equalsIgnoreCase("Self-emp-inc")) {
							income_class = ">50K";
					}
				} else 		if( age_class.equalsIgnoreCase("U55")) {
					if( workclass.equalsIgnoreCase("State-gov")) {
							//
					} else 			if( workclass.equalsIgnoreCase("Self-emp-not-inc")) {
							income_class = "<=50K";
					} else 			if( workclass.equalsIgnoreCase("Private")) {
						if( hours_class.equalsIgnoreCase("UPTO40")) {
							if( race.equalsIgnoreCase("White")) {
								if( marital_status.equalsIgnoreCase("Never-married")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Married-civ-spouse")) {
									if( sex.equalsIgnoreCase("Male")) {
											//
									} else 							if( sex.equalsIgnoreCase("Female")) {
											//
									}
								} else 						if( marital_status.equalsIgnoreCase("Divorced")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Married-spouse-absent")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Separated")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Married-AF-spouse")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Widowed")) {
										//
								}
							} else 					if( race.equalsIgnoreCase("Black")) {
									//
							} else 					if( race.equalsIgnoreCase("Asian-Pac-Islander")) {
									income_class = ">50K";
							} else 					if( race.equalsIgnoreCase("Amer-Indian-Eskimo")) {
									//
							} else 					if( race.equalsIgnoreCase("Other")) {
									//
							}
						} else 				if( hours_class.equalsIgnoreCase("UPTO20")) {
								//
						} else 				if( hours_class.equalsIgnoreCase("UPTO60")) {
								income_class = "<=50K";
						} else 				if( hours_class.equalsIgnoreCase("UPTO80")) {
								//
						} else 				if( hours_class.equalsIgnoreCase("80PLUS")) {
								//
						}
					} else 			if( workclass.equalsIgnoreCase("Federal-gov")) {
							//
					} else 			if( workclass.equalsIgnoreCase("Local-gov")) {
							income_class = ">50K";
					} else 			if( workclass.equalsIgnoreCase("?")) {
							income_class = ">50K";
					} else 			if( workclass.equalsIgnoreCase("Self-emp-inc")) {
							income_class = ">50K";
					}
				} else 		if( age_class.equalsIgnoreCase("U30")) {
						income_class = "<=50K";
				} else 		if( age_class.equalsIgnoreCase("55PLUS")) {
						income_class = "<=50K";
				} else 		if( age_class.equalsIgnoreCase("U21")) {
						income_class = "<=50K";
				}
			} else 	if( education.equalsIgnoreCase("Assoc-acdm")) {
				if( workclass.equalsIgnoreCase("State-gov")) {
						income_class = "<=50K";
				} else 		if( workclass.equalsIgnoreCase("Self-emp-not-inc")) {
						income_class = "<=50K";
				} else 		if( workclass.equalsIgnoreCase("Private")) {
					if( race.equalsIgnoreCase("White")) {
						if( age_class.equalsIgnoreCase("U40")) {
							if( hours_class.equalsIgnoreCase("UPTO40")) {
									income_class = ">50K";
							} else 					if( hours_class.equalsIgnoreCase("UPTO20")) {
									//
							} else 					if( hours_class.equalsIgnoreCase("UPTO60")) {
									income_class = "<=50K";
							} else 					if( hours_class.equalsIgnoreCase("UPTO80")) {
									//
							} else 					if( hours_class.equalsIgnoreCase("80PLUS")) {
									//
							}
						} else 				if( age_class.equalsIgnoreCase("U55")) {
							if( hours_class.equalsIgnoreCase("UPTO40")) {
									income_class = "<=50K";
							} else 					if( hours_class.equalsIgnoreCase("UPTO20")) {
									//
							} else 					if( hours_class.equalsIgnoreCase("UPTO60")) {
									income_class = ">50K";
							} else 					if( hours_class.equalsIgnoreCase("UPTO80")) {
									//
							} else 					if( hours_class.equalsIgnoreCase("80PLUS")) {
									//
							}
						} else 				if( age_class.equalsIgnoreCase("U30")) {
								//
						} else 				if( age_class.equalsIgnoreCase("55PLUS")) {
								//
						} else 				if( age_class.equalsIgnoreCase("U21")) {
								//
						}
					} else 			if( race.equalsIgnoreCase("Black")) {
							income_class = ">50K";
					} else 			if( race.equalsIgnoreCase("Asian-Pac-Islander")) {
							//
					} else 			if( race.equalsIgnoreCase("Amer-Indian-Eskimo")) {
							//
					} else 			if( race.equalsIgnoreCase("Other")) {
							//
					}
				} else 		if( workclass.equalsIgnoreCase("Federal-gov")) {
						//
				} else 		if( workclass.equalsIgnoreCase("Local-gov")) {
						//
				} else 		if( workclass.equalsIgnoreCase("?")) {
						//
				} else 		if( workclass.equalsIgnoreCase("Self-emp-inc")) {
						//
				}
			} else 	if( education.equalsIgnoreCase("Assoc-voc")) {
				if( age_class.equalsIgnoreCase("U40")) {
					if( workclass.equalsIgnoreCase("State-gov")) {
							//
					} else 			if( workclass.equalsIgnoreCase("Self-emp-not-inc")) {
							//
					} else 			if( workclass.equalsIgnoreCase("Private")) {
						if( marital_status.equalsIgnoreCase("Never-married")) {
								//
						} else 				if( marital_status.equalsIgnoreCase("Married-civ-spouse")) {
							if( race.equalsIgnoreCase("White")) {
								if( sex.equalsIgnoreCase("Male")) {
									if( hours_class.equalsIgnoreCase("UPTO40")) {
											//
									} else 							if( hours_class.equalsIgnoreCase("UPTO20")) {
											//
									} else 							if( hours_class.equalsIgnoreCase("UPTO60")) {
											//
									} else 							if( hours_class.equalsIgnoreCase("UPTO80")) {
											//
									} else 							if( hours_class.equalsIgnoreCase("80PLUS")) {
											//
									}
								} else 						if( sex.equalsIgnoreCase("Female")) {
										//
								}
							} else 					if( race.equalsIgnoreCase("Black")) {
									//
							} else 					if( race.equalsIgnoreCase("Asian-Pac-Islander")) {
									//
							} else 					if( race.equalsIgnoreCase("Amer-Indian-Eskimo")) {
									//
							} else 					if( race.equalsIgnoreCase("Other")) {
									//
							}
						} else 				if( marital_status.equalsIgnoreCase("Divorced")) {
								//
						} else 				if( marital_status.equalsIgnoreCase("Married-spouse-absent")) {
								//
						} else 				if( marital_status.equalsIgnoreCase("Separated")) {
								//
						} else 				if( marital_status.equalsIgnoreCase("Married-AF-spouse")) {
								//
						} else 				if( marital_status.equalsIgnoreCase("Widowed")) {
								//
						}
					} else 			if( workclass.equalsIgnoreCase("Federal-gov")) {
							//
					} else 			if( workclass.equalsIgnoreCase("Local-gov")) {
							//
					} else 			if( workclass.equalsIgnoreCase("?")) {
							//
					} else 			if( workclass.equalsIgnoreCase("Self-emp-inc")) {
							//
					}
				} else 		if( age_class.equalsIgnoreCase("U55")) {
					if( workclass.equalsIgnoreCase("State-gov")) {
						if( marital_status.equalsIgnoreCase("Never-married")) {
								//
						} else 				if( marital_status.equalsIgnoreCase("Married-civ-spouse")) {
							if( race.equalsIgnoreCase("White")) {
								if( sex.equalsIgnoreCase("Male")) {
									if( hours_class.equalsIgnoreCase("UPTO40")) {
											//
									} else 							if( hours_class.equalsIgnoreCase("UPTO20")) {
											//
									} else 							if( hours_class.equalsIgnoreCase("UPTO60")) {
											//
									} else 							if( hours_class.equalsIgnoreCase("UPTO80")) {
											//
									} else 							if( hours_class.equalsIgnoreCase("80PLUS")) {
											//
									}
								} else 						if( sex.equalsIgnoreCase("Female")) {
										//
								}
							} else 					if( race.equalsIgnoreCase("Black")) {
									//
							} else 					if( race.equalsIgnoreCase("Asian-Pac-Islander")) {
									//
							} else 					if( race.equalsIgnoreCase("Amer-Indian-Eskimo")) {
									//
							} else 					if( race.equalsIgnoreCase("Other")) {
									//
							}
						} else 				if( marital_status.equalsIgnoreCase("Divorced")) {
								//
						} else 				if( marital_status.equalsIgnoreCase("Married-spouse-absent")) {
								//
						} else 				if( marital_status.equalsIgnoreCase("Separated")) {
								//
						} else 				if( marital_status.equalsIgnoreCase("Married-AF-spouse")) {
								//
						} else 				if( marital_status.equalsIgnoreCase("Widowed")) {
								//
						}
					} else 			if( workclass.equalsIgnoreCase("Self-emp-not-inc")) {
							//
					} else 			if( workclass.equalsIgnoreCase("Private")) {
							income_class = ">50K";
					} else 			if( workclass.equalsIgnoreCase("Federal-gov")) {
							//
					} else 			if( workclass.equalsIgnoreCase("Local-gov")) {
							income_class = ">50K";
					} else 			if( workclass.equalsIgnoreCase("?")) {
							//
					} else 			if( workclass.equalsIgnoreCase("Self-emp-inc")) {
							//
					}
				} else 		if( age_class.equalsIgnoreCase("U30")) {
						//
				} else 		if( age_class.equalsIgnoreCase("55PLUS")) {
						income_class = "<=50K";
				} else 		if( age_class.equalsIgnoreCase("U21")) {
						//
				}
			} else 	if( education.equalsIgnoreCase("7th-8th")) {
				if( age_class.equalsIgnoreCase("U40")) {
						income_class = "<=50K";
				} else 		if( age_class.equalsIgnoreCase("U55")) {
					if( workclass.equalsIgnoreCase("State-gov")) {
							//
					} else 			if( workclass.equalsIgnoreCase("Self-emp-not-inc")) {
							//
					} else 			if( workclass.equalsIgnoreCase("Private")) {
						if( marital_status.equalsIgnoreCase("Never-married")) {
								//
						} else 				if( marital_status.equalsIgnoreCase("Married-civ-spouse")) {
							if( race.equalsIgnoreCase("White")) {
								if( sex.equalsIgnoreCase("Male")) {
									if( hours_class.equalsIgnoreCase("UPTO40")) {
											//
									} else 							if( hours_class.equalsIgnoreCase("UPTO20")) {
											//
									} else 							if( hours_class.equalsIgnoreCase("UPTO60")) {
											//
									} else 							if( hours_class.equalsIgnoreCase("UPTO80")) {
											//
									} else 							if( hours_class.equalsIgnoreCase("80PLUS")) {
											//
									}
								} else 						if( sex.equalsIgnoreCase("Female")) {
										//
								}
							} else 					if( race.equalsIgnoreCase("Black")) {
									//
							} else 					if( race.equalsIgnoreCase("Asian-Pac-Islander")) {
									//
							} else 					if( race.equalsIgnoreCase("Amer-Indian-Eskimo")) {
									//
							} else 					if( race.equalsIgnoreCase("Other")) {
									//
							}
						} else 				if( marital_status.equalsIgnoreCase("Divorced")) {
								//
						} else 				if( marital_status.equalsIgnoreCase("Married-spouse-absent")) {
								//
						} else 				if( marital_status.equalsIgnoreCase("Separated")) {
								//
						} else 				if( marital_status.equalsIgnoreCase("Married-AF-spouse")) {
								//
						} else 				if( marital_status.equalsIgnoreCase("Widowed")) {
								//
						}
					} else 			if( workclass.equalsIgnoreCase("Federal-gov")) {
							//
					} else 			if( workclass.equalsIgnoreCase("Local-gov")) {
							//
					} else 			if( workclass.equalsIgnoreCase("?")) {
							//
					} else 			if( workclass.equalsIgnoreCase("Self-emp-inc")) {
							income_class = ">50K";
					}
				} else 		if( age_class.equalsIgnoreCase("U30")) {
						income_class = "<=50K";
				} else 		if( age_class.equalsIgnoreCase("55PLUS")) {
						//
				} else 		if( age_class.equalsIgnoreCase("U21")) {
						//
				}
			} else 	if( education.equalsIgnoreCase("Doctorate")) {
					income_class = ">50K";
			} else 	if( education.equalsIgnoreCase("Prof-school")) {
					income_class = ">50K";
			} else 	if( education.equalsIgnoreCase("5th-6th")) {
					income_class = "<=50K";
			} else 	if( education.equalsIgnoreCase("10th")) {
					income_class = "<=50K";
			} else 	if( education.equalsIgnoreCase("1st-4th")) {
					income_class = "<=50K";
			} else 	if( education.equalsIgnoreCase("Preschool")) {
					//
			} else 	if( education.equalsIgnoreCase("12th")) {
					//
			}
		} else if( relationship.equalsIgnoreCase("Wife")) {
			if( education.equalsIgnoreCase("Bachelors")) {
				if( race.equalsIgnoreCase("White")) {
						income_class = ">50K";
				} else 		if( race.equalsIgnoreCase("Black")) {
						income_class = "<=50K";
				} else 		if( race.equalsIgnoreCase("Asian-Pac-Islander")) {
						//
				} else 		if( race.equalsIgnoreCase("Amer-Indian-Eskimo")) {
						//
				} else 		if( race.equalsIgnoreCase("Other")) {
						//
				}
			} else 	if( education.equalsIgnoreCase("HS-grad")) {
				if( age_class.equalsIgnoreCase("U40")) {
					if( race.equalsIgnoreCase("White")) {
						if( hours_class.equalsIgnoreCase("UPTO40")) {
							if( workclass.equalsIgnoreCase("State-gov")) {
									//
							} else 					if( workclass.equalsIgnoreCase("Self-emp-not-inc")) {
									//
							} else 					if( workclass.equalsIgnoreCase("Private")) {
								if( marital_status.equalsIgnoreCase("Never-married")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Married-civ-spouse")) {
									if( sex.equalsIgnoreCase("Male")) {
											//
									} else 							if( sex.equalsIgnoreCase("Female")) {
											//
									}
								} else 						if( marital_status.equalsIgnoreCase("Divorced")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Married-spouse-absent")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Separated")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Married-AF-spouse")) {
										//
								} else 						if( marital_status.equalsIgnoreCase("Widowed")) {
										//
								}
							} else 					if( workclass.equalsIgnoreCase("Federal-gov")) {
									//
							} else 					if( workclass.equalsIgnoreCase("Local-gov")) {
									//
							} else 					if( workclass.equalsIgnoreCase("?")) {
									//
							} else 					if( workclass.equalsIgnoreCase("Self-emp-inc")) {
									//
							}
						} else 				if( hours_class.equalsIgnoreCase("UPTO20")) {
								//
						} else 				if( hours_class.equalsIgnoreCase("UPTO60")) {
								income_class = "<=50K";
						} else 				if( hours_class.equalsIgnoreCase("UPTO80")) {
								//
						} else 				if( hours_class.equalsIgnoreCase("80PLUS")) {
								//
						}
					} else 			if( race.equalsIgnoreCase("Black")) {
							//
					} else 			if( race.equalsIgnoreCase("Asian-Pac-Islander")) {
							income_class = "<=50K";
					} else 			if( race.equalsIgnoreCase("Amer-Indian-Eskimo")) {
							//
					} else 			if( race.equalsIgnoreCase("Other")) {
							//
					}
				} else 		if( age_class.equalsIgnoreCase("U55")) {
					if( hours_class.equalsIgnoreCase("UPTO40")) {
						if( workclass.equalsIgnoreCase("State-gov")) {
								//
						} else 				if( workclass.equalsIgnoreCase("Self-emp-not-inc")) {
								income_class = ">50K";
						} else 				if( workclass.equalsIgnoreCase("Private")) {
							if( marital_status.equalsIgnoreCase("Never-married")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Married-civ-spouse")) {
								if( race.equalsIgnoreCase("White")) {
									if( sex.equalsIgnoreCase("Male")) {
											//
									} else 							if( sex.equalsIgnoreCase("Female")) {
											//
									}
								} else 						if( race.equalsIgnoreCase("Black")) {
										//
								} else 						if( race.equalsIgnoreCase("Asian-Pac-Islander")) {
										//
								} else 						if( race.equalsIgnoreCase("Amer-Indian-Eskimo")) {
										//
								} else 						if( race.equalsIgnoreCase("Other")) {
										//
								}
							} else 					if( marital_status.equalsIgnoreCase("Divorced")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Married-spouse-absent")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Separated")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Married-AF-spouse")) {
									//
							} else 					if( marital_status.equalsIgnoreCase("Widowed")) {
									//
							}
						} else 				if( workclass.equalsIgnoreCase("Federal-gov")) {
								//
						} else 				if( workclass.equalsIgnoreCase("Local-gov")) {
								//
						} else 				if( workclass.equalsIgnoreCase("?")) {
								//
						} else 				if( workclass.equalsIgnoreCase("Self-emp-inc")) {
								//
						}
					} else 			if( hours_class.equalsIgnoreCase("UPTO20")) {
							income_class = "<=50K";
					} else 			if( hours_class.equalsIgnoreCase("UPTO60")) {
							//
					} else 			if( hours_class.equalsIgnoreCase("UPTO80")) {
							//
					} else 			if( hours_class.equalsIgnoreCase("80PLUS")) {
							//
					}
				} else 		if( age_class.equalsIgnoreCase("U30")) {
						//
				} else 		if( age_class.equalsIgnoreCase("55PLUS")) {
						income_class = ">50K";
				} else 		if( age_class.equalsIgnoreCase("U21")) {
						income_class = "<=50K";
				}
			} else 	if( education.equalsIgnoreCase("11th")) {
					//
			} else 	if( education.equalsIgnoreCase("Masters")) {
					income_class = "<=50K";
			} else 	if( education.equalsIgnoreCase("9th")) {
					//
			} else 	if( education.equalsIgnoreCase("Some-college")) {
				if( hours_class.equalsIgnoreCase("UPTO40")) {
					if( workclass.equalsIgnoreCase("State-gov")) {
							//
					} else 			if( workclass.equalsIgnoreCase("Self-emp-not-inc")) {
							//
					} else 			if( workclass.equalsIgnoreCase("Private")) {
							income_class = "<=50K";
					} else 			if( workclass.equalsIgnoreCase("Federal-gov")) {
							//
					} else 			if( workclass.equalsIgnoreCase("Local-gov")) {
							income_class = "<=50K";
					} else 			if( workclass.equalsIgnoreCase("?")) {
							income_class = ">50K";
					} else 			if( workclass.equalsIgnoreCase("Self-emp-inc")) {
							//
					}
				} else 		if( hours_class.equalsIgnoreCase("UPTO20")) {
						income_class = ">50K";
				} else 		if( hours_class.equalsIgnoreCase("UPTO60")) {
						income_class = ">50K";
				} else 		if( hours_class.equalsIgnoreCase("UPTO80")) {
						//
				} else 		if( hours_class.equalsIgnoreCase("80PLUS")) {
						//
				}
			} else 	if( education.equalsIgnoreCase("Assoc-acdm")) {
					income_class = ">50K";
			} else 	if( education.equalsIgnoreCase("Assoc-voc")) {
					income_class = "<=50K";
			} else 	if( education.equalsIgnoreCase("7th-8th")) {
					//
			} else 	if( education.equalsIgnoreCase("Doctorate")) {
					//
			} else 	if( education.equalsIgnoreCase("Prof-school")) {
					income_class = ">50K";
			} else 	if( education.equalsIgnoreCase("5th-6th")) {
					//
			} else 	if( education.equalsIgnoreCase("10th")) {
					income_class = "<=50K";
			} else 	if( education.equalsIgnoreCase("1st-4th")) {
					//
			} else 	if( education.equalsIgnoreCase("Preschool")) {
					//
			} else 	if( education.equalsIgnoreCase("12th")) {
					//
			}
		} else if( relationship.equalsIgnoreCase("Own-child")) {
				income_class = "<=50K";
		} else if( relationship.equalsIgnoreCase("Unmarried")) {
			if( education.equalsIgnoreCase("Bachelors")) {
					income_class = "<=50K";
			} else 	if( education.equalsIgnoreCase("HS-grad")) {
					income_class = "<=50K";
			} else 	if( education.equalsIgnoreCase("11th")) {
					income_class = "<=50K";
			} else 	if( education.equalsIgnoreCase("Masters")) {
				if( workclass.equalsIgnoreCase("State-gov")) {
						income_class = "<=50K";
				} else 		if( workclass.equalsIgnoreCase("Self-emp-not-inc")) {
						income_class = ">50K";
				} else 		if( workclass.equalsIgnoreCase("Private")) {
						income_class = "<=50K";
				} else 		if( workclass.equalsIgnoreCase("Federal-gov")) {
						//
				} else 		if( workclass.equalsIgnoreCase("Local-gov")) {
						//
				} else 		if( workclass.equalsIgnoreCase("?")) {
						//
				} else 		if( workclass.equalsIgnoreCase("Self-emp-inc")) {
						income_class = "<=50K";
				}
			} else 	if( education.equalsIgnoreCase("9th")) {
					//
			} else 	if( education.equalsIgnoreCase("Some-college")) {
				if( age_class.equalsIgnoreCase("U40")) {
						income_class = "<=50K";
				} else 		if( age_class.equalsIgnoreCase("U55")) {
						income_class = "<=50K";
				} else 		if( age_class.equalsIgnoreCase("U30")) {
						income_class = "<=50K";
				} else 		if( age_class.equalsIgnoreCase("55PLUS")) {
					if( workclass.equalsIgnoreCase("State-gov")) {
							//
					} else 			if( workclass.equalsIgnoreCase("Self-emp-not-inc")) {
							income_class = "<=50K";
					} else 			if( workclass.equalsIgnoreCase("Private")) {
							income_class = ">50K";
					} else 			if( workclass.equalsIgnoreCase("Federal-gov")) {
							//
					} else 			if( workclass.equalsIgnoreCase("Local-gov")) {
							//
					} else 			if( workclass.equalsIgnoreCase("?")) {
							//
					} else 			if( workclass.equalsIgnoreCase("Self-emp-inc")) {
							//
					}
				} else 		if( age_class.equalsIgnoreCase("U21")) {
						//
				}
			} else 	if( education.equalsIgnoreCase("Assoc-acdm")) {
					income_class = "<=50K";
			} else 	if( education.equalsIgnoreCase("Assoc-voc")) {
					income_class = "<=50K";
			} else 	if( education.equalsIgnoreCase("7th-8th")) {
					//
			} else 	if( education.equalsIgnoreCase("Doctorate")) {
					//
			} else 	if( education.equalsIgnoreCase("Prof-school")) {
					//
			} else 	if( education.equalsIgnoreCase("5th-6th")) {
					//
			} else 	if( education.equalsIgnoreCase("10th")) {
					income_class = "<=50K";
			} else 	if( education.equalsIgnoreCase("1st-4th")) {
					income_class = "<=50K";
			} else 	if( education.equalsIgnoreCase("Preschool")) {
					//
			} else 	if( education.equalsIgnoreCase("12th")) {
					//
			}
		} else if( relationship.equalsIgnoreCase("Other-relative")) {
			if( education.equalsIgnoreCase("Bachelors")) {
					income_class = "<=50K";
			} else 	if( education.equalsIgnoreCase("HS-grad")) {
					income_class = "<=50K";
			} else 	if( education.equalsIgnoreCase("11th")) {
					//
			} else 	if( education.equalsIgnoreCase("Masters")) {
					income_class = ">50K";
			} else 	if( education.equalsIgnoreCase("9th")) {
					income_class = "<=50K";
			} else 	if( education.equalsIgnoreCase("Some-college")) {
					income_class = "<=50K";
			} else 	if( education.equalsIgnoreCase("Assoc-acdm")) {
					//
			} else 	if( education.equalsIgnoreCase("Assoc-voc")) {
					//
			} else 	if( education.equalsIgnoreCase("7th-8th")) {
					//
			} else 	if( education.equalsIgnoreCase("Doctorate")) {
					//
			} else 	if( education.equalsIgnoreCase("Prof-school")) {
					//
			} else 	if( education.equalsIgnoreCase("5th-6th")) {
					income_class = "<=50K";
			} else 	if( education.equalsIgnoreCase("10th")) {
					//
			} else 	if( education.equalsIgnoreCase("1st-4th")) {
					//
			} else 	if( education.equalsIgnoreCase("Preschool")) {
					//
			} else 	if( education.equalsIgnoreCase("12th")) {
					income_class = "<=50K";
			}
		}

		System.out.println("predicted: " + income_class + ";\t" + 
											age_class + "\t" + workclass + "\t" + education + "\t" + marital_status + "\t" + 
											relationship + "\t" + race + "\t" + sex + "\t" + hours_class);
		return income_class;
	}
}