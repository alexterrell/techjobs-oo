package org.launchcode.controllers;

import org.launchcode.models.Job;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model,int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job someJob = jobData.findById(id);
        model.addAttribute("job", someJob);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        if (jobForm.getName() == "") {
            model.addAttribute("errors", errors);
            return "new-job";
        } else {
            Job newJob = new Job(jobForm.getName(), jobForm.findE(jobForm.getEmployerId()), jobForm.findL(jobForm.getLocationId()),
                    jobForm.findP(jobForm.getPositionTypeId()), jobForm.findC(jobForm.getCoreCompetencyId()));
            jobData.add(newJob);
            int id = newJob.getId();
            return "redirect:/job?id=" + id;
            // *Rather than rendering job detail template here, pass newJob.getId() into index* //
        }
    }}