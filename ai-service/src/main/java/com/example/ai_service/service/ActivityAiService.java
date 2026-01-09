package com.example.ai_service.service;

import com.example.ai_service.model.Activity;
import com.example.ai_service.model.Recomendation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAiService {
    private final GeminiService geminiService;

    public String generateRecomendation(Activity activity){
        String prompt = createPromptForActivity(activity);
        String aiResponse = geminiService.getAnswer(prompt);
        log.info("RESPOSE FROM AI: {}", aiResponse);
        return aiResponse;
    }

    private String createPromptForActivity(Activity activity) {
        return String.format("""
                Analyze this fitness activity and provide details recomendations in the following
                {
                 "analysis":{
                    "overall":"Overall analysis here",
                    "pace":"Pac analysis here",
                    "heartRate":"Heart Rate analysis here",
                    "caloriesBurned":"Calories analysis here"
                    },
                    "improvements":[
                    {
                        "area":"Area name",
                        "recomendation":"Detailed recomendation"
                     }
                     ],
                     "suggestions":[
                     {
                        "workout":"Workout name",
                        "description":"Detailed workout description"
                     }
                     ],
                     "safety":[
                     {
                        "point":"Safety point 1",
                        "description":"Safety description 1"
                      }
                      ]
                      }
                      Analyze this activity:
                      Activity Type: %s
                      Duration: %d minutes
                      Calories Burned: %d

                      Provided detailed analysis focusing on performence,improvements,next workout suggesion,and safety
                      Ensure the response follows the EXACT JSON format shown above.
                """,
                        activity.getType(),
                        activity.getDuration(),
                        activity.getCalories()
                        );
    }
}
